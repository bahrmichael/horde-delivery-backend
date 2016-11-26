package de.bahr.order;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

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
}