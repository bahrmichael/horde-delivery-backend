package de.bahr.order;

import java.util.List;

/**
 * Created by michaelbahr on 07/11/16.
 */
public class OrderUtils {

    private static final Double PILOT_MARGIN = 0.1;

    public static Double calculateTotalVolume(List<Order> orders) {
        Double totalVolume = 0.0;
        for (Order order : orders) {
            for (Item item : order.getItems()) {
                totalVolume += item.getVolume() * item.getQuantity();
            }
        }

        return totalVolume;
    }

    public static Double calculateProfit(List<Order> orders) {
        Double total = calculateTotalValue(orders);
        return total * PILOT_MARGIN;
    }

    public static Double calculateTotalValue(List<Order> orders) {
        final Double[] total = {0.0};
        orders.forEach(order -> total[0] += order.getExpectedPrice());
        return total[0];
    }
}
