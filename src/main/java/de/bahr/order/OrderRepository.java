package de.bahr.order;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findOrderById(String id);

    Long countByStatus(String status);
}