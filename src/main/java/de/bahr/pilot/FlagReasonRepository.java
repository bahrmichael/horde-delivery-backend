package de.bahr.pilot;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by michaelbahr on 01/08/16.
 */
public interface FlagReasonRepository extends MongoRepository<FlagReason, String> {
}
