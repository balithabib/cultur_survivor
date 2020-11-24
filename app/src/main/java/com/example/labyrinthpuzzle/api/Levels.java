package com.example.labyrinthpuzzle.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Levels {

    public static final String QUESTIONS_FILED = "levels";

    @JsonProperty(QUESTIONS_FILED)
    private final List<LevelApi> levels;

    public Levels(@JsonProperty(QUESTIONS_FILED) final List<LevelApi> levels) {
        this.levels = levels;
    }

    public List<LevelApi> getLevels() {
        return levels;
    }

    @Override
    public String toString() {
        return "Levels{" +
                "levels=" + levels +
                '}';
    }
}
