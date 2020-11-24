package com.example.labyrinthpuzzle.model;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public Position setRow(int row) {
        this.row = row;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public Position setColumn(int column) {
        this.column = column;
        return this;
    }
}
