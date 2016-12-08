package de.bahr.pilot;

import de.bahr.manager.ManageController;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
