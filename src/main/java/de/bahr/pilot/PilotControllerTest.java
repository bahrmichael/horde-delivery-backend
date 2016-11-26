package de.bahr.pilot;

import de.bahr.order.Order;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by michaelbahr on 26/11/16.
 */
public class PilotControllerTest {

    private PilotUtil sut = new PilotUtil();

    @Test
    public void filterForPilot() {
        List<Order> list = new ArrayList<>();

        Order withoutAssignee_no = new Order();
        withoutAssignee_no.setAssignee(null);
        list.add(withoutAssignee_no);

        Order withWrongAssignee_no = new Order();
        withWrongAssignee_no.setAssignee("someoneelse");
        list.add(withWrongAssignee_no);

        Order withCorrectAssignee_yes = new Order();
        withCorrectAssignee_yes.setAssignee("test");
        list.add(withCorrectAssignee_yes);

        List<Order> result = sut.filterForPilot(list, "test");

        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getAssignee());
    }

    @Test
    public void updateToContracted() throws InterruptedException {
        List<Order> list = new ArrayList<>();
        list.add(new Order());
        list.add(new Order());

        LocalDateTime before = LocalDateTime.now(Clock.systemUTC());

        // run
        Thread.sleep(10L);
        sut.updateToContracted(list);

        // verify
        assertEquals(2, list.size());
        for (Order order : list) {
            assertEquals("contracted", order.getStatus());
            assertNotNull(order.getCompleted());
            assertTrue(before.isBefore(order.getCompleted()));
        }


    }

}