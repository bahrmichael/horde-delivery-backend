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
            if (item.getName().equals("Mexallon")) {
                boolean isCorrectAmount = item.getQuantity() == 8655;
                assertTrue(isCorrectAmount);
            } else if (item.getName().equals("Pyerite")) {
                boolean isCorrectAmount = item.getQuantity() == 14327;
                assertTrue(isCorrectAmount);
            }

            System.out.println(item.getName() + " x" + item.getQuantity());
        }
    }

    @Test
    public void bug_20161122_2() throws Exception {
        String link = "http://evepraisal.com/e/11856686";
        List<Item> items = sut.requestItems(link);
        for (Item item : items) {
            if (item.getName().equals("Cormorant")) {
                boolean isCorrectAmount = item.getQuantity() == 1;
                assertTrue(isCorrectAmount);
            } else if (item.getName().equals("Caldari Navy Antimatter Charge S")) {
                boolean isCorrectAmount = item.getQuantity() == 1000;
                assertTrue(isCorrectAmount);
            }

            System.out.println(item.getName() + " x" + item.getQuantity());
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