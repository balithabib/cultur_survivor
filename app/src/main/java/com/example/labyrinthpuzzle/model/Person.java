package com.example.labyrinthpuzzle.model;

public class Person {
    private int x;
    private int y;
    private QuestionBasic question;
    // image

    public Person(QuestionBasic question) {
        this.question = question;
    }

    public int getX() {
        return x;
    }

    public Person setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Person setY(int y) {
        this.y = y;
        return this;
    }

    public QuestionBasic getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "Person{" +
                "x=" + x +
                ", y=" + y +
                ", question=" + question +
                '}';
    }
}
