package de.bahr.order;

import de.bahr.DataStore;
import de.bahr.Http;
import de.bahr.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DataStore dataStore;

    @Autowired
    SlackService slackService;

    private static final double DELIVERY_FEE = 0.13;
    private static final double PILOT_MARGIN = 0.8;

    private static final String EVEPRAISAL_PATTERN = "http[s]?://(www\\.)?evepraisal\\.com/e/[0-9]*";
    private static final String ID = "[a-z0-9]*";

    private Pattern pattern = Pattern.compile(EVEPRAISAL_PATTERN);
    private Pattern idPattern = Pattern.compile(ID);

    @RequestMapping(value = "/lowitems", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> lowItems() {

        List<Order> lowOrders = orderRepository.findAll().stream().filter(order -> null != order.getExpectedPrice() && order.getExpectedPrice() < 10000000.0).collect(Collectors.toList());

        Map<String, Long> result = new HashMap<>();

        for (Order order : lowOrders) {
            List<Item> items = order.getItems();
            for (Item item : items) {
                String name = item.getName();
                Long quantity = item.getQuantity();

                if (result.containsKey(name)) {
                    result.put(name, result.get(name) + quantity);
                } else {
                    result.put(name, quantity);
                }
            }
        }

        result = sortByValue(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> ((Comparable<V>) o1.getValue()).compareTo(o2.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> status(@RequestParam String id) {


        // validate url
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findOne(id);
        String status = order.getStatus();

        return new ResponseEntity<>("{ \"status\": \"" + status + "\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/quote", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> quote(@RequestParam String link, @RequestParam Integer multiplier) {

        // validate url
        Matcher matcher = pattern.matcher(link);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long price;
        // call url
        try {
            List<Item> items = requestItems(link);
            price = calculateQuote(items);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{ \"price\": " + ( price * multiplier ) + "}", HttpStatus.OK);
    }

    @RequestMapping(value = "/shippingprice", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> shippingPrice(@RequestParam String link) {

        // validate url
        Matcher matcher = pattern.matcher(link);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Double shippingPrice;
        // call url
        try {
            List<Item> items = requestItems(link);
            Long stackPrice = calculateStackPrice(items);
            // 0.13 for delivery service; 0.8 of that for the logistics pilot
            // todo: assure that this is used in the frontend (it is not yet)
            shippingPrice = stackPrice * DELIVERY_FEE * PILOT_MARGIN;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{ \"price\": " + shippingPrice.intValue() + "}", HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Order order, @RequestParam Integer multiplier) {

        // validate url
        if (!isPraisalValid(order.getLink())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // call url
        List<Item> items;
        try {
            items = requestItems(order.getLink());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (null != multiplier && multiplier > 1) {
            for (Item item : items) {
                item.setQuantity(item.getQuantity() * 2);
            }
        }

        order.setItems(items);
        order.setStatus("requested");
        Order savedOrder = orderRepository.save(order);

        try {
            slackService.sendNotification(order.getClient(), order.getExpectedPrice());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    private boolean isPraisalValid(String link) {
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    private List<Item> requestItems(String link) throws Exception {
        StringBuffer get = Http.getRequest(link, "GET");
        String[] rawItems = extractRawItems(get);
        return parseItems(rawItems);
    }

    private String[] extractRawItems(StringBuffer get) {
        String html = get.toString();
        String body = html.split("<body>")[1];
        String resultContainer = body.split("<div class=\"span7\" id=\"result_container\">")[1];
        String tableBody = resultContainer.split("<tbody>")[1].split("</tbody>")[0];
        return tableBody.split("<tr class=\"line-item-row \">");
    }

    private Integer calculateSpaiShippingPrice(List<Item> items) {
        Double totalPrice = 0.0;

        for (Item item : items) {
            Long price = item.getPrice();
            Long quantity = item.getQuantity();
            Double volume = item.getVolume();

            double stackPrice = price * quantity;
            double shippingCollateralFee = stackPrice * 0.02;
            double stackVolume = volume * quantity;
            double shippingVolumeFee = stackVolume * 300;

            totalPrice += stackPrice + shippingCollateralFee + shippingVolumeFee;
        }

        return totalPrice.intValue();
    }

    private Long calculateQuote(List<Item> items) {
        return (long) (calculateStackPrice(items) * (1 + DELIVERY_FEE));
    }

    private Long calculateStackPrice(List<Item> items) {
        Long totalPrice = 0L;

        for (Item item : items) {
            Long price = Long.valueOf(item.getPrice());
            Long quantity = Long.valueOf(item.getQuantity());

            totalPrice += price * quantity;
        }

        return totalPrice;
    }

    private List<Item> parseItems(String[] rawItems) throws Exception {
        List<Item> result = new ArrayList<>();
        for (String rawItem : rawItems) {
            if (!rawItem.replace(" ", "").isEmpty()) {
                result.add(parseItem(rawItem));
            }
        }
        return result;
    }

    private Item parseItem(String rawItem) throws Exception {

        String rawQuantity = rawItem.split("<td style=\"text-align:right\">")[1].split("</td>")[0];
        String itemName = rawItem.split("target=\"_blank\">")[2].split("</a>")[0];
        itemName = itemName.replace("&#39;", "'");

        String rawItemPrice = rawItem.split("<span class=\"nowrap\">")[1].split("</span>")[0];

        Long price = Double.valueOf(rawItemPrice.replace(",", "")).longValue();
        Long quantity = Double.valueOf(rawQuantity.replace(",", "")).longValue();

        Item item = dataStore.find(itemName);
        Double volume = item.getVolume();

        return new Item(itemName, quantity, volume, price);
    }

}
