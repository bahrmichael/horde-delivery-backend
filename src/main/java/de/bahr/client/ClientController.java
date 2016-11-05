package de.bahr.client;

import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/client")
public class ClientController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    private String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> name(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(user, OK);
    }

    @RequestMapping(value = "/queue", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> queue(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);

        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> notContracted(order) && orderBelongsToClient(user.getName(), order))
                .collect(Collectors.toList());

        return new ResponseEntity<>(orders, OK);
    }

    private boolean notContracted(Order order) {
        return !order.getStatus().equals("contracted");
    }

    private User getUser(@RequestHeader("authorization") String auth) {
        String decoded = decode(auth.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        return userRepository.findByCharacterId(Long.valueOf(characterId));
    }

    private boolean orderBelongsToClient(String user, Order order) {
        return order.getClient() != null && order.getClient().toLowerCase().equals(user.toLowerCase());
    }

}
