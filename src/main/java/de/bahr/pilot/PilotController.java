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


    private String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    @RequestMapping(value = "/list/shipping", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listShipping(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);

        List<Order> result = orderRepository.findShippingOrders().stream()
                .filter(order -> order.getAssignee() != null
                        && user.getName().equals(order.getAssignee())).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> name(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private User getUser(@RequestHeader("authorization") String auth) {
        String decoded = decode(auth.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        return userRepository.findByCharacterId(Long.valueOf(characterId));
    }

    @RequestMapping(value = "/update/status", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> updateStatus(@RequestParam String id, @RequestParam String newStatus) {
        return manageController.updateStatus(id, newStatus);
    }

    @RequestMapping(value = "/contracted/all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> contractedAll(@RequestHeader("authorization") String authorization) {
        String user = getUser(authorization).getName();

        List<Order> shippingOrders = orderRepository.findShippingOrders().stream()
                .filter(order -> orderBelongToUser(user, order)).collect(Collectors.toList());

        for (Order order : shippingOrders) {
            order.setStatus("contracted");
            order.setCompleted(LocalDateTime.now(Clock.systemUTC()));
        }

        orderRepository.save(shippingOrders);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean orderBelongToUser(String user, Order order) {
        return order.getAssignee() != null && order.getAssignee().equals(user);
    }

}
