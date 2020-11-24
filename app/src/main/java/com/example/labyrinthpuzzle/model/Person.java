package com.example.labyrinthpuzzle.model;

import com.example.labyrinthpuzzle.model.question.QuestionBasic;

public class Person {
    private int row;
    private int column;
    private QuestionBasic question;
    // image

    public Person(QuestionBasic question) {
        this.question = question;
    }

    public int getRow() {
        return row;
    }

    public Person setRow(int row) {
        this.row = row;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public Person setColumn(int column) {
        this.column = column;
        return this;
    }

    public QuestionBasic getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "Person{" +
                "row=" + row +
                ", column=" + column +
                ", question=" + question +
                '}';
    }
}
