package com.example.labyrinthpuzzle.model;

import java.util.List;

public class QuestionWithImage extends QuestionBasic {
    private final List<String> urlImages;

    public QuestionWithImage(String question, List<String> answers, int correctedAnswer, List<String> urlImages) {
        super(question, answers, correctedAnswer);
        this.urlImages = urlImages;
    }

    public List<String> getUrlImages() {
        return urlImages;
    }

    @Override
    public String toString() {
        return "QuestionWithImage{" +
                "urlImages=" + urlImages +
                '}';
    }
}
