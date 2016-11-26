package de.bahr.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by michaelbahr on 26/11/16.
 */
public class OrderUtilTest {

    @Test
    public void calcAge_hours() throws Exception {
        for (int i = 0; i < 100; i++) {
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC()).minusHours(i);

            long age = OrderUtil.calcAge(now);

            assertEquals(i, age);
        }
    }

    @Test
    public void calcAge_minutes() throws Exception {
        for (int i = 0; i < 100; i++) {
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC()).minusMinutes(i);

            long age = OrderUtil.calcAge(now);
            int ageInHours = i / 60;

            assertEquals(ageInHours, age);
        }
    }

    @Test
    public void setAges() {
        List<Order> list = new ArrayList<>();
        list.add(createOrderWithAge(0));
        list.add(createOrderWithAge(2));

        OrderUtil.setAges(list);

        assertEquals(0L, list.get(0).getAge().longValue());
        assertEquals(2L, list.get(1).getAge().longValue());
    }

    private Order createOrderWithAge(int hours) {
        Order order = new Order();
        LocalDateTime created = LocalDateTime.now(Clock.systemUTC()).minusHours(hours);
        order.setCreated(created);
        return order;
    }
}