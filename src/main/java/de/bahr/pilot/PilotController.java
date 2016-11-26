package de.bahr.pilot;

import de.bahr.manager.ManageController;
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

    @RequestMapping(value = "/list/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listShipping(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        List<Order> result = filterForPilot(orderRepository.findShippingOrders(), user.getName());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    protected List<Order> filterForPilot(List<Order> shippingOrders, String pilotName) {
        return shippingOrders.stream()
                .filter(order -> order.getAssignee() != null
                        && pilotName.equals(order.getAssignee())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> name(@RequestHeader("authorization") String auth) {
        return new ResponseEntity<>(getUser(auth, userRepository), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/status", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateStatus(@RequestParam String id, @RequestParam String newStatus) {
        return manageController.updateStatus(id, newStatus);
    }

    @RequestMapping(value = "/contracted/all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> contractedAll(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        List<Order> shippingOrders = filterForPilot(orderRepository.findShippingOrders(), user.getName());

        updateToContracted(shippingOrders);

        orderRepository.save(shippingOrders);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void updateToContracted(List<Order> shippingOrders) {
        for (Order order : shippingOrders) {
            order.setStatus("contracted");
            order.setCompleted(LocalDateTime.now(Clock.systemUTC()));
        }
    }

}
