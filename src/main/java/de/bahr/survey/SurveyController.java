package de.bahr.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by michaelbahr on 13/04/16.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/survey")
public class SurveyController {

    @Autowired
    SurveyAnswerRepository answerRepository;

    @Autowired
    SurveyQuestionRepository questionRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getQuestion(@RequestParam String uuid) {

        List<SurveyAnswer> answers = answerRepository.findByUuid(uuid);
        for (SurveyAnswer answer : answers) {
            long days = answer.getDate().until( LocalDateTime.now(), ChronoUnit.DAYS);
            if (days < 3) {
                // empty response means no question to answer
                return new ResponseEntity<>("{}", HttpStatus.NO_CONTENT);
            }
        }

        List<SurveyQuestion> allQuestions = questionRepository.findAll();

        int randomNum = ThreadLocalRandom.current().nextInt(0, allQuestions.size());
        SurveyQuestion question = allQuestions.get(randomNum);

        return new ResponseEntity<>("{ \"question\": \"" + question.getQuestion() + "\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> quote(@RequestBody SurveyAnswer answer) {
        answerRepository.save(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
