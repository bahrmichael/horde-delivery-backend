package de.bahr.pilot;

import de.bahr.DataStore;
import de.bahr.manager.ManageController;
import de.bahr.order.Item;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static de.bahr.user.UserUtil.getUser;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/pilot")
public class PilotController {

    @Autowired
    PilotRepository pilotRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ManageController manageController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PilotUtil pilotUtil;

    @Autowired
    DataStore dataStore;

    @RequestMapping(value = "/list/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listShipping(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        List<Order> result = pilotUtil.filterForPilot(orderRepository.findShippingOrders(), user.getName());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/status", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateStatus(@RequestParam String id, @RequestParam String newStatus) {
        return manageController.updateStatus(id, newStatus);
    }

    @RequestMapping(value = "/contracted/all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> contractedAll(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        List<Order> shippingOrders = pilotUtil.filterForPilot(orderRepository.findShippingOrders(), user.getName());
        pilotUtil.updateToContracted(shippingOrders);
        orderRepository.save(shippingOrders);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/cargo", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> sumConfirmedFor(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        List<Order> ordersPending = getOrders("requested", user.getName());
        List<Order> ordersShipping = getOrders("shipping", user.getName());

        Double cargoPending;
        Double cargoShipping;
        try {
            cargoPending = calcTotalVolume(ordersPending);
            cargoShipping = calcTotalVolume(ordersShipping);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{ \"cargoPending\" :" + cargoPending
                + ", \"cargoShipping\" :" + cargoShipping + "}", HttpStatus.OK);
    }

    private List<Order> getOrders(String status, String pilot) {
        return orderRepository.findByStatusAndAssignee(status, pilot);
    }

    private Double calcTotalVolume(List<Order> orders) throws Exception {
        Double totalVolume = 0.0;
        for (Order order : orders) {
            for (Item item : order.getItems()) {
                Item dataStoreItem = dataStore.find(item.getName());
                if (null != dataStoreItem) {
                    totalVolume += dataStoreItem.getVolume() * item.getQuantity();
                }
            }
        }
        return totalVolume;
    }

}
