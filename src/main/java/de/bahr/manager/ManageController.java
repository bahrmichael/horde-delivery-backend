package de.bahr.manager;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.bahr.AuthorizationInterceptor;
import de.bahr.DataStore;
import de.bahr.order.Item;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.order.OrderUtil;
import de.bahr.stats.StatsController;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    StatsController statsController;

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
            order.setCompleted(LocalDateTime.now(Clock.systemUTC()));
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
        List<Order> result = orderRepository.findNonCompletedOrders();

        OrderUtil.setAges(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/requested", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumRequested() {
        return statsController.sumRequested();
    }

    @RequestMapping(value = "/sum/confirmed", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumConfirmed() {
        return statsController.sumConfirmed();
    }

    @RequestMapping(value = "/sum/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumShipping() {
        return statsController.sumShipping();
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
        return statsController.volumeShipping();
    }

    @RequestMapping(value = "/volume/pending", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumeRequested() {
        return statsController.volumeRequested();
    }

    @RequestMapping(value = "/consolidate", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> consolidate(@RequestParam String client, @RequestParam String destination) {

        if (null == client || null == destination) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Order> orders = orderRepository.findPendingOrders().stream()
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

        String link;

        try {
            HttpResponse<String> response = Unirest.post("http://hordedelivery.com:8000")
                    .header("accept", "application/text").body(concatItems(items)).asString();
            if (response.getStatus() == 200) {
                link = response.getBody();
            } else {
                System.out.println("Response has status " + response.getStatus());
                System.out.println(response.getBody());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Order consolidated = new Order(client, link, price, destination);
        consolidated.setItems(items);
        consolidated.setCreated(created);
        orderRepository.save(consolidated);

        orderRepository.delete(orders);

        return new ResponseEntity<>(HttpStatus.OK);
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

    @RequestMapping(value = "/count/requests", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> countRequests() {
        return statsController.countRequests();
    }

    @RequestMapping(value = "/names", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> pilotNames() {
        List<User> allUsers = userRepository.findAll().stream()
                .filter(user -> user.getRole().contains("PILOT")).collect(Collectors.toList());
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Bean
    @Autowired
    public MappedInterceptor getMappedInterceptor(AuthorizationInterceptor authorizationInterceptor) {
        return new MappedInterceptor(new String[]{"/**"}, authorizationInterceptor);
    }

}
