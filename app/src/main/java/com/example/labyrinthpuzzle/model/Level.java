package com.example.labyrinthpuzzle.model;

import java.util.List;

public class Level {
    private String title;
    private List<Person> persons;
    private String sound;
    private String background;
    // introduction
    // son

    public Level(String title, List<Person> persons, String sound, String background) {
        this.title = title;
        this.persons = persons;
        this.sound = sound;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public String getSound() {
        return sound;
    }

    public String getBackground() {
        return background;
    }

    @Override
    public String toString() {
        return "Level{" +
                "title='" + title + '\'' +
                ", persons=" + persons +
                ", sound='" + sound + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
