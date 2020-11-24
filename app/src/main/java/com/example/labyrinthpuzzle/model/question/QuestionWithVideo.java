package com.example.labyrinthpuzzle.model.question;

import java.util.List;

public class QuestionWithVideo extends QuestionBasic {
    private final String urlVideo;

    public QuestionWithVideo(String question, List<String> answers, int correctedAnswer, String urlVideo) {
        super(question, answers, correctedAnswer);
        this.urlVideo = urlVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    @Override
    public String toString() {
        return "QuestionWithVideo{" +
                "urlVideo='" + urlVideo + '\'' +
                '}';
    }
}
