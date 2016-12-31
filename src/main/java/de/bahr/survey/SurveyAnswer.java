package de.bahr.survey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by michaelbahr on 31/12/2016.
 */
public class SurveyAnswer {
    private String question;
    private String answer;
    private String uuid;

    @JsonCreator
    public SurveyAnswer(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                        @JsonProperty("uuid") String uuid) {
        this.question = question;
        this.answer = answer;
        this.uuid = uuid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
