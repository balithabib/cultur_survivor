package com.example.labyrinthpuzzle.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LevelApi {
    public static final String NAME_FILED = "name";
    public static final String IMAGE_FILED = "image";
    public static final String SOUND_FILED = "sound";
    public static final String DESCRIPTION_FILED = "description";
    public static final String QUESTIONS_FILED = "questions";

    @JsonProperty(NAME_FILED)
    private final String name;

    @JsonProperty(DESCRIPTION_FILED)
    private final String description;

    @JsonProperty(IMAGE_FILED)
    private final String image;

    @JsonProperty(SOUND_FILED)
    private final String sound;

    @JsonProperty(QUESTIONS_FILED)
    private final List<QuestionApi> questions;


    public LevelApi(@JsonProperty(NAME_FILED) final String name,
                    @JsonProperty(DESCRIPTION_FILED) final String description,
                    @JsonProperty(IMAGE_FILED) final String image,
                    @JsonProperty(SOUND_FILED) final String sound,
                    @JsonProperty(QUESTIONS_FILED) final List<QuestionApi> questions) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.sound = sound;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getSound() {
        return sound;
    }

    public List<QuestionApi> getQuestions() {
        return questions;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "LevelApi{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", sound='" + sound + '\'' +
                ", questions=" + questions +
                '}';
    }
}
