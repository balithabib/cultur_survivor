package com.example.labyrinthpuzzle.model;

import java.util.List;

public class QuestionBasic {
    private String question;
    private List<String> answers;
    private int correctedAnswer;

    public QuestionBasic(String question, List<String> answers, int correctedAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctedAnswer = correctedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectedAnswer() {
        return correctedAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", correctedAnswer=" + correctedAnswer +
                '}';
    }
}
