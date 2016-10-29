package de.bahr.user;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByCharacterId(Long characterId);
}