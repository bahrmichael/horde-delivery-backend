package de.bahr.stats;

import de.bahr.AuthorizationInterceptor;
import de.bahr.DataStore;
import de.bahr.order.Item;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.util.List;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/pilot")
public class StatsController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DataStore dataStore;

    @Autowired
    UserRepository userRepository;

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

        orderRepository.findByStatus(status).stream().filter(
                order -> order.getStatus().equals(status)
        ).forEach(order -> totalValue[0] += order.getExpectedPrice());
        return totalValue[0];
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
        List<Order> shippingOrders = orderRepository.findByStatus("shipping");

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

        List<Order> shippingOrders = orderRepository.findPendingOrders();

        try {
            Double totalVolume = calcTotalVolume(shippingOrders);
            return new ResponseEntity<>("{ \"volumePending\" : " + totalVolume.intValue() + " }", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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

    @Bean
    @Autowired
    public MappedInterceptor getMappedInterceptor(AuthorizationInterceptor authorizationInterceptor) {
        return new MappedInterceptor(new String[]{"/**"}, authorizationInterceptor);
    }

}
