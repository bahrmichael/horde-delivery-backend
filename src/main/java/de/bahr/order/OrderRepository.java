package de.bahr.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    Long countByStatus(String status);

    List<Order> findByStatus(String status);

    @Query("{ $or: [{ 'status': 'requested'}, { 'status': 'confirmed'}, { 'status': 'shipping'}]}")
    List<Order> findNonCompletedOrders();

    @Query("{ $or: [{ 'status': 'requested'}, { 'status': 'confirmed'}]}")
    List<Order> findPendingOrders();

    @Query("{ 'status': 'shipping'}")
    List<Order> findShippingOrders();

    List<Order> findByAssigneeAndStatus(String assignee, String status);
}