package de.bahr.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface VolumesRepository extends MongoRepository<Volumes, String> {

    @Query("{ 'typeid' : ?0 }")
    List<Volumes> findByTypeId(Long typeId);
}