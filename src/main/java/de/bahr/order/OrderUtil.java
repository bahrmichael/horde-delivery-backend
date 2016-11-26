package de.bahr.order;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by michaelbahr on 26/11/16.
 */
public class OrderUtil {

    public static long calcAge(LocalDateTime created) {
        return created.until(LocalDateTime.now(Clock.systemUTC()), ChronoUnit.HOURS);
    }

    public static void setAges(List<Order> orders) {
        orders.forEach(order -> order.setAge(OrderUtil.calcAge(order.getCreated())));
    }
}
