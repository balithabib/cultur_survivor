package com.example.labyrinthpuzzle.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestionApi {
    public static final String QUESTION_FILED = "question";
    public static final String ANSWERS_FILED = "answers";
    public static final String RIGHT_ANSWER_FILED = "right_answer";

    @JsonProperty(QUESTION_FILED)
    private final String question;

    @JsonProperty(ANSWERS_FILED)
    private final List<String> answers;

    @JsonProperty(RIGHT_ANSWER_FILED)
    private final Integer rightAnswer;

    public QuestionApi(@JsonProperty(QUESTION_FILED) final String question,
                       @JsonProperty(ANSWERS_FILED) final List<String> answers,
                       @JsonProperty(RIGHT_ANSWER_FILED) final Integer rightAnswer) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
