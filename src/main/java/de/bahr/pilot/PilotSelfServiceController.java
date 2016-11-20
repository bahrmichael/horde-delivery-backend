package de.bahr.pilot;

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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @RequestMapping(value = "/list/requested", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listRequested(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);

        List<Order> result = orderRepository.findByStatus("requested").stream()
                .filter(order -> order.getAssignee() == null || order.getAssignee().equals(user.getName()))
                .collect(Collectors.toList());

        result.forEach(order -> order.setAge(calcAge(order.getCreated())));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private long calcAge(LocalDateTime created) {
        return created.until( LocalDateTime.now(Clock.systemUTC()), ChronoUnit.HOURS);
    }

    @RequestMapping(value = "/pick", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> pickOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);

        if (Objects.equals(order.getAssignee(), user.getName())) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else if (order.getAssignee() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setAssignee(user.getName());
        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/flag", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> flagOrder(@RequestHeader("authorization") String auth, String orderId, String reason) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);
        if (!order.getAssignee().equals(user.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setAssignee("flagged");
        orderRepository.save(order);

        FlagReason flagReason = new FlagReason(user.getName(), orderId, reason);
        flagReasonRepository.save(flagReason);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/bought", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> boughtOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);
        if (!Objects.equals(order.getAssignee(), user.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setStatus("shipping");
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/skip", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> skipOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);
        if (!Objects.equals(order.getAssignee(), user.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setStatus("requested");
        order.setAssignee(null);
        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    private User getUser(@RequestHeader("authorization") String auth) {
        String decoded = decode(auth.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        return userRepository.findByCharacterId(Long.valueOf(characterId));
    }

    private String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

}
