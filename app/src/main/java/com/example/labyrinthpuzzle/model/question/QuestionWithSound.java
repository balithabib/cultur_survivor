package com.example.labyrinthpuzzle.model.question;

import java.util.List;

public class QuestionWithSound extends QuestionBasic {
    private final String urlSound;

    public QuestionWithSound(String question, List<String> answers, int correctedAnswer, String urlSound) {
        super(question, answers, correctedAnswer);
        this.urlSound = urlSound;
    }

    public String getUrlSound() {
        return urlSound;
    }

    @Override
    public String toString() {
        return "QuestionWithSound{" +
                "urlSound='" + urlSound + '\'' +
                '}';
    }
}
