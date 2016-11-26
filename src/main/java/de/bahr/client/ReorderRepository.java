package de.bahr.client;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface ReorderRepository extends MongoRepository<Reorder, String> {

    List<Reorder> findAllByOwner(String owner);
}