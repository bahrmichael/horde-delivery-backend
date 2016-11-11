package de.bahr.pilot;

import de.bahr.manager.ManageController;
import de.bahr.order.Item;
import de.bahr.order.Order;
import de.bahr.order.OrderRepository;
import de.bahr.user.User;
import de.bahr.user.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static de.bahr.order.OrderUtils.calculateProfit;
import static de.bahr.order.OrderUtils.calculateTotalValue;
import static de.bahr.order.OrderUtils.calculateTotalVolume;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/secured/pilot/stats")
public class PilotStatsController {

    private static final Double PILOT_MARGIN = 0.1;

    @Autowired
    PilotRepository pilotRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ManageController manageController;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/volume", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volume(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        Double now = calcTotalVolumeNow(user);
        Double past = calcTotalVolumePast(user);
        String response = createNowPastJson(now, past);
        return new ResponseEntity<>(response, OK);
    }

    @RequestMapping(value = "/volume/now", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumeNow(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcTotalVolumeNow(user), OK);
    }

    @RequestMapping(value = "/volume/past", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> volumePast(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcTotalVolumePast(user), OK);
    }


    @RequestMapping(value = "/value", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> value(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        Double now = calcTotalValueNow(user);
        Double past = calcTotalValuePast(user);
        return new ResponseEntity<>(createNowPastJson(now, past), OK);
    }

    @RequestMapping(value = "/value/now", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> valueNow(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcTotalValueNow(user), OK);
    }

    private Double calcTotalValueNow(User user) {
        List<Order> orders = findOrdersNow(user);
        return calculateTotalValue(orders);
    }

    @RequestMapping(value = "/value/past", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> valuePast(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcTotalValuePast(user), OK);
    }

    private Double calcTotalValuePast(User user) {
        List<Order> orders = findOrdersPast(user);
        return calculateTotalValue(orders);
    }

    @RequestMapping(value = "/profit", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> profit(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        Double now = calcProfitNow(user);
        Double past = calcProfitPast(user);
        return new ResponseEntity<>(createNowPastJson(now, past), OK);
    }

    @RequestMapping(value = "/profit/now", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> valueProfitNow(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcProfitNow(user), OK);
    }

    private Double calcProfitNow(User user) {
        List<Order> orders = findOrdersNow(user);
        return calculateProfit(orders);
    }

    @RequestMapping(value = "/profit/past", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> valueProfitPast(@RequestHeader("authorization") String auth) {
        User user = getUser(auth);
        return new ResponseEntity<>(calcProfitPast(user), OK);
    }

    private Double calcProfitPast(User user) {
        List<Order> orders = findOrdersPast(user);
        return calculateProfit(orders);
    }

    private Double calcTotalVolumePast(User user) {
        List<Order> orders = findOrdersPast(user);
        return calculateTotalVolume(orders);
    }

    private String createNowPastJson(Double now, Double past) {
        return "{ \"now\": " + now + ",\"past\": " + past + "}";
    }

    private Double calcTotalVolumeNow(User user) {
        List<Order> orders = findOrdersNow(user);
        return calculateTotalVolume(orders);
    }

    private List<Order> findOrdersPast(User user) {
        return findOrders(user, "contracted");
    }

    private List<Order> findOrdersNow(User user) {
        List<Order> orders = new ArrayList<>();
        orders.addAll(findOrders(user, "confirmed"));
        orders.addAll(findOrders(user, "shipping"));
        return orders;
    }

    private List<Order> findOrders(User user, String status) {
        return orderRepository.findByAssigneeAndStatus(user.getName(), status);
    }

    private String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    // todo: try to remove this request header annotation
    private User getUser(@RequestHeader("authorization") String auth) {
        String decoded = decode(auth.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        return userRepository.findByCharacterId(Long.valueOf(characterId));
    }
}
