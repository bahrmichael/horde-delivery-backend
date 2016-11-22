package de.bahr.order;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelbahr on 13/11/16.
 */
public class OrderControllerTest {

    private OrderController sut = new OrderController();

    @Test
    public void bug_20161122_pre() throws Exception {
        String link = "http://evepraisal.com/e/13750448";
        List<Item> items = sut.requestItems(link);
        boolean isCormorant = items.get(0).getName().equals("Cormorant");
        assertTrue(isCormorant);
    }

    @Test
    public void bug_20161122() throws Exception {
        String link = "http://evepraisal.com/e/13749962";
        List<Item> items = sut.requestItems(link);
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    @Test
    public void testItemConcatenation() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Tritanium", 3L, 1.0, 1L));
        items.add(new Item("Avatar", 1L, 1.0, 1L));

        String result = sut.concatItems(items);

        assertEquals("Tritanium x3\nAvatar x1", result);
    }

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