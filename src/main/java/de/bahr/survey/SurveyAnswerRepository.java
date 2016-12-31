package de.bahr.survey;

import de.bahr.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by michaelbahr on 07/04/16.
 */
public interface SurveyAnswerRepository extends MongoRepository<SurveyAnswer, String> {

    List<SurveyAnswer> findByUuid(String uuid);
}