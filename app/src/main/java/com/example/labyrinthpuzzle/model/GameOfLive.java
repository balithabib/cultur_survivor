package com.example.labyrinthpuzzle.model;

public class GameOfLive {
    private static final int LIVELY = 0;
    private static final int DEAD = 1;

    private int[][] cells;

    public GameOfLive(final int size) {
        this.cells = new int[size][size];
    }


    //br1
    // if lively cell < 2 dead
    // if lively cell > 3 dead
    // if dead cell == 3 lively
    //br2

    //br3



}
