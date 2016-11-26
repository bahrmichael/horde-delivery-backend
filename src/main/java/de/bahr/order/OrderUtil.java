package de.bahr.order;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by michaelbahr on 26/11/16.
 */
public class OrderUtil {

    public static long calcAge(LocalDateTime created) {
        return created.until(LocalDateTime.now(Clock.systemUTC()), ChronoUnit.HOURS);
    }
}
