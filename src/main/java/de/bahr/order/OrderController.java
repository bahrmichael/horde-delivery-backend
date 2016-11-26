package de.bahr.order;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.bahr.DataStore;
import de.bahr.SlackService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Value("${ALPHA_CODE}")
    private String ALPHA_CODE;

    private static final double DELIVERY_FEE = 0.13;
    private static final double PILOT_MARGIN = 0.8;

    private static final String EVEPRAISAL_PATTERN = "http[s]?://(www\\.)?evepraisal\\.com/e/[0-9]*";
    private static final String ID = "[a-z0-9]*";

    private Pattern pattern = Pattern.compile(EVEPRAISAL_PATTERN);
    private Pattern idPattern = Pattern.compile(ID);

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

    @RequestMapping(value = "/alpha", method = RequestMethod.POST)
    public ResponseEntity<?> alphaOrder(@RequestPart("link") String link, @RequestPart("client") String client, @RequestPart("authorization") String auth) {

        if (!auth.equals(ALPHA_CODE)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Order order = new Order(client, link, 0.0, "7RM Beanstar");
        return create(order, 1);
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
            price = getPrice(link);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{ \"price\": " + (price * multiplier) + "}", HttpStatus.OK);
    }

    protected Long getPrice(@RequestParam String link) throws Exception {
        Long price;List<Item> items = requestItems(link);
        price = calculateQuote(items);
        return price;
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

        multiplyItems(multiplier, items);
        order.setItems(items);
        order.setStatus("requested");

        if (multiplier > 1) {
            try {
                HttpResponse<String> response = Unirest.post("http://evepraisal:8000")
                        .header("accept", "application/text").body(concatItems(items)).asString();
                if (response.getStatus() == 200) {
                    order.setLink(response.getBody());
                } else {
                    System.out.println("Response has status " + response.getStatus());
                    System.out.println(response.getBody());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (UnirestException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Order savedOrder = orderRepository.save(order);

        try {
            slackService.sendNotification(order.getClient(), order.getExpectedPrice());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    protected String concatItems(List<Item> items) {
        String result = "";
        for (Item item : items) {
            result += item.getName() + " x" + item.getQuantity() + "\n";
        }
        // remove last \n
        result = result.substring(0, result.length() - 1);
        return result;
    }

    protected void multiplyItems(@RequestParam Integer multiplier, List<Item> items) {
        if (null != multiplier && multiplier > 1) {
            for (Item item : items) {
                item.setQuantity(item.getQuantity() * multiplier);
            }
        }
    }

    private boolean isPraisalValid(String link) {
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    protected List<Item> requestItems(String link) throws Exception {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(link + ".json").asJson();
        if (jsonResponse.getStatus() != 200) {
            throw new IllegalStateException("The call to " + link + " returned a " + jsonResponse.getStatus() + " response.");
        }

        JSONObject body = jsonResponse.getBody().getObject();
        JSONArray items = body.getJSONArray("items");

        List<Item> result = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            parseItem(result, items.get(i));
        }

        return result;
    }

    private void parseItem(List<Item> result, Object obj) throws Exception {
        JSONObject item = (JSONObject) obj;

        String itemName = item.getString("name");
        Long quantity = item.getLong("quantity");
        Double volume = item.getDouble("volume");
        if (null != dataStore) {
            Item dataStoreItem = dataStore.find(itemName);
            if (dataStoreItem != null) {
                volume = dataStoreItem.getVolume();
            }
        }
        JSONObject prices = item.getJSONObject("prices");
        JSONObject sell = prices.getJSONObject("sell");
        Double sellPrice = sell.getDouble("price");

        result.add(new Item(itemName, quantity, volume, sellPrice.longValue()));
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

}
