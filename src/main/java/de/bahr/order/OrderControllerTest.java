package de.bahr.order;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by michaelbahr on 13/11/16.
 */
public class OrderControllerTest {

    private OrderController sut = new OrderController();

    @Test
    public void multiplyItems() throws Exception {
        Long count = 10L;

        for (int j = 1; j < 10; j++) {
            Integer multiplier = j;
            List<Item> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                list.add(new Item("test", (long) i, 1.0, 1000L));
            }

            sut.multiplyItems(multiplier, list);

            for (int i = 0; i < count; i++) {
                Long quantity = list.get(i).getQuantity();
                assertEquals((long) i * multiplier, (long) quantity);
            }
        }
    }

}