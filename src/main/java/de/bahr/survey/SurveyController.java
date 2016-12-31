package de.bahr.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<?> getQuestion() {

        List<SurveyQuestion> allQuestions = questionRepository.findAll();

        int randomNum = ThreadLocalRandom.current().nextInt(0, allQuestions.size());
        SurveyQuestion question = allQuestions.get(randomNum);

        return new ResponseEntity<>(question.getQuestion(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> quote(@RequestBody SurveyAnswer answer) {
        answerRepository.save(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
