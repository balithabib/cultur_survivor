package com.example.labyrinthpuzzle.model;

import java.util.List;

public class Level {
    private final LabyrinthModel labyrinth;
    private String title;
    private String description;
    private List<Person> persons;
    private String sound;
    private String background;

    // introduction
    // son

    public Level(final String title, final String description, final List<Person> persons, final String sound, final String background, final LabyrinthModel labyrinth) {
        this.title = title;
        this.description = description;
        this.persons = persons;
        this.sound = sound;
        this.background = background;
        this.labyrinth = labyrinth;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public LabyrinthModel getLabyrinth() {
        return labyrinth;
    }

    public int getNumbersOfPeople() {
        return this.persons.size();
    }

    @Override
    public String toString() {
        return "Level{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", persons=" + persons +
                ", sound='" + sound + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
