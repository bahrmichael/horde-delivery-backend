package de.bahr.pilot;

import de.bahr.order.Order;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by michaelbahr on 26/11/16.
 */
@Component
public class PilotUtil {

    protected List<Order> filterForPilot(List<Order> shippingOrders, String pilotName) {
        return shippingOrders.stream()
                .filter(order -> order.getAssignee() != null
                        && orderBelongsToPilot(pilotName, order)).collect(Collectors.toList());
    }

    protected void updateToContracted(List<Order> shippingOrders) {
        for (Order order : shippingOrders) {
            order.setStatus("contracted");
            order.setCompleted(LocalDateTime.now(Clock.systemUTC()));
        }
    }

    protected boolean orderBelongsToPilot(String pilotName, Order order) {
        return Objects.equals(order.getAssignee(), pilotName);
    }


}
