package de.bahr.pilot;

import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.order.OrderUtil;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.bahr.user.UserUtil.getUser;

/**
 * Created by michaelbahr on 11/11/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/pilot/selfservice")
public class PilotSelfServiceController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlagReasonRepository flagReasonRepository;

    @Autowired
    PilotUtil pilotUtil;


    @RequestMapping(value = "/list/requested", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listRequested(@RequestHeader("authorization") String auth) {
        User user = getUser(auth, userRepository);

        // show orders that don't have an assignee or are assigned to the current pilot
        List<Order> orders = orderRepository.findByStatus("requested").stream()
                .filter(order -> order.getAssignee() == null || order.getAssignee().equals(user.getName()))
                .collect(Collectors.toList());
        OrderUtil.setAges(orders);

        // sort from oldest to youngest
        orders.sort(Comparator.comparing(Order::getAge));
        Collections.reverse(orders);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(value = "/pick", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> pickOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth, userRepository);

        Order order = orderRepository.findOne(orderId);

        boolean alreadyBelongsToPilot = Objects.equals(order.getAssignee(), user.getName());
        if (alreadyBelongsToPilot) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else if (order.getAssignee() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            order.setAssignee(user.getName());
            orderRepository.save(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> flagOrder(@RequestHeader("authorization") String auth, String orderId, String reason) {
        User user = getUser(auth, userRepository);
        Order order = orderRepository.findOne(orderId);
        if (!pilotUtil.orderBelongsToPilot(user.getName(), order)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        order.setAssignee("flagged");
        orderRepository.save(order);

        FlagReason flagReason = new FlagReason(user.getName(), orderId, reason);
        flagReasonRepository.save(flagReason);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/bought", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> boughtOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth, userRepository);
        Order order = orderRepository.findOne(orderId);
        if (!pilotUtil.orderBelongsToPilot(user.getName(), order)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        order.setStatus("shipping");
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/skip", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> skipOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth, userRepository);
        Order order = orderRepository.findOne(orderId);
        if (!pilotUtil.orderBelongsToPilot(user.getName(), order)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        order.setStatus("requested");
        order.setAssignee(null);
        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
