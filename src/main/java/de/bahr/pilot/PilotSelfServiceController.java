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

import java.util.List;

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


    @RequestMapping(value = "/list/requested", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listRequested() {
        List<Order> result = orderRepository.findByStatus("requested");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/pick", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> pickOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);
        if (order.getAssignee() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setAssignee(user.getName());
        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/flag", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> flagOrder(@RequestHeader("authorization") String auth, String orderId) {
        User user = getUser(auth);

        Order order = orderRepository.findOne(orderId);
        if (!order.getAssignee().equals(user.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        order.setAssignee("Rihan Shazih");
        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
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
