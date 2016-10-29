package de.bahr.manager;

import de.bahr.AuthorizationInterceptor;
import de.bahr.DataStore;
import de.bahr.order.Item;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/manager")
public class ManageController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DataStore dataStore;

    @Autowired
    UserRepository userRepository;

    private static final String ID = "[a-z0-9]*";

    private Pattern idPattern = Pattern.compile(ID);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(@RequestParam String id) {
        // validate orderId
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        orderRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/status", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateStatus(@RequestParam String id, @RequestParam String newStatus) {
        // validate orderId
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findOne(id);
        order.setStatus(newStatus);
        if (newStatus.equals("contracted")) {
            order.setCompleted(LocalDateTime.now());
        }
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/assignee", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateAssignee(@RequestParam String id, @RequestParam String assignee) {
        // validate orderId
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findOne(id);
        order.setAssignee(assignee);
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/price", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updatePrice(@RequestParam String id, @RequestParam String price) {
        // validate url
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findOne(id);
        order.setExpectedPrice(Double.valueOf(price));
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/shippingPrice", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateShippingPrice(@RequestParam String id, @RequestParam String shippingPrice) {
        // validate url
        Matcher matcher = idPattern.matcher(id);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderRepository.findOne(id);
        order.setShippingPrice(Double.valueOf(shippingPrice));
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> list() {
        List<Order> all = orderRepository.findAll();
        List<Order> result = new ArrayList<>();
        all.stream().filter(order -> null != order).forEach(order -> {
            boolean contracted = "contracted".equals(order.getStatus());
            boolean rejected = "rejected".equals(order.getStatus());
            if (!contracted && !rejected) {
                result.add(order);
            }
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/requested", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumRequested() {
        Double value = sumStatus("requested");
        return new ResponseEntity<>("{ \"sum\" :" + value + " }", HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/confirmed", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumConfirmed() {
        Double value = sumStatus("confirmed");
        return new ResponseEntity<>("{ \"sum\" :" + value + " }", HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumShipping() {
        Double value = sumStatus("shipping");
        return new ResponseEntity<>("{ \"sum\" :" + value + " }", HttpStatus.OK);
    }

    private Double sumStatus(String status) {
        final Double[] totalValue = {0.0};
        orderRepository.findAll().stream().filter(
                order -> order.getStatus().equals(status)
        ).forEach(order -> totalValue[0] += order.getExpectedPrice());
        return totalValue[0];
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> load(@RequestParam String orderId) {

        Optional<Order> searchResult = orderRepository.findAll().stream()
                .filter(order -> order.getId().equals(orderId)).findFirst();

        if (searchResult.isPresent()) {
            return new ResponseEntity<>(searchResult.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/volume", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumes(@RequestParam String name) {

        Item dataStoreItem;
        try {
            dataStoreItem = dataStore.find(name);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (null != dataStoreItem) {
            return new ResponseEntity<>(dataStoreItem.getVolume(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/volume/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumeShipping() {
        List<Order> shippingOrders = orderRepository.findAll().stream().filter(order -> order.getStatus()
                .equals("shipping")).collect(Collectors.toList());

        try {
            Double totalVolume = calcTotalVolume(shippingOrders);
            return new ResponseEntity<>("{ \"volumeShipping\" : " + totalVolume.intValue() + " }", HttpStatus.OK);
        } catch (Exception e) {
            // todo: catch SocketException when cluster is down
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/volume/pending", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumeRequested() {
        List<Order> shippingOrders = orderRepository.findAll().stream().filter(order -> order.getStatus()
                .equals("requested") || order.getStatus().equals("confirmed")).collect(Collectors.toList());

        try {
            Double totalVolume = calcTotalVolume(shippingOrders);
            return new ResponseEntity<>("{ \"volumePending\" : " + totalVolume.intValue() + " }", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/consolidate", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> consolidate(@RequestParam String client, @RequestParam String destination) {

        if (null == client || null == destination) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> canBeConsolidated(order, client, destination)).collect(Collectors.toList());

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        LocalDateTime created = null;
        Double price = 0.0;
        List<Item> items = new ArrayList<>();
        for (Order order : orders) {
            price += order.getExpectedPrice();
            items.addAll(order.getItems());
            created = order.getCreated();
        }

        Order consolidated = new Order(client, "http://evepraisal.com", price, destination);
        consolidated.setItems(items);
        consolidated.setCreated(created);
        orderRepository.save(consolidated);

        orderRepository.delete(orders);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean canBeConsolidated(Order order, String client, String destination) {
        boolean isRequested = order.getStatus().equals("requested");
        if (!isRequested) {
            return false;
        }
        boolean clientMatches = client.toLowerCase().equals(order.getClient().toLowerCase());
        if (!clientMatches) {
            return false;
        }
        boolean destinationMatches = destination.toLowerCase().equals(order.getDestination().toLowerCase());
        return destinationMatches;
    }

    private Double calcTotalVolume(List<Order> shippingOrders) throws Exception {
        Double totalVolume = 0.0;
        for (Order order : shippingOrders) {
            for (Item item : order.getItems()) {
                Item dataStoreItem = dataStore.find(item.getName());
                if (null != dataStoreItem) {
                    totalVolume += dataStoreItem.getVolume() * item.getQuantity();
                }
            }
        }
        return totalVolume;
    }

    @RequestMapping(value = "/count/requests", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> countRequests() {
        Long requested = orderRepository.countByStatus("requested");
        return new ResponseEntity<>("{\"requestCount\": " + requested + "}", HttpStatus.OK);
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> pilotNames() {
        List<User> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Bean
    @Autowired
    public MappedInterceptor getMappedInterceptor(AuthorizationInterceptor authorizationInterceptor) {
        return new MappedInterceptor(new String[]{"/**"}, authorizationInterceptor);
    }

}
