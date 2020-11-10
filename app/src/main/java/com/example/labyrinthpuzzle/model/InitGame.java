package com.example.labyrinthpuzzle.model;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.labyrinthpuzzle.service.Service;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class InitGame {
    List<Pair<Level, LabyrinthModel>> pairLevelAndLabyrinth;
    int numberOfLevel = 7;
    Service service = new Service();

    public InitGame() {
        this.pairLevelAndLabyrinth = generateLevelsAndLabyrinths();
    }

    private List<Pair<Level, LabyrinthModel>> generateLevelsAndLabyrinths() {
        List<Pair<Level, LabyrinthModel>> levels = new ArrayList<>();
        for (int levelNumber = 0; levelNumber < numberOfLevel; levelNumber++) {
            LabyrinthModel labyrinthModel = new LabyrinthModel(13);
            labyrinthModel.generate();
            levels.add(Pair.create(generateLevel(levelNumber, labyrinthModel), labyrinthModel));
        }
        return levels;
    }

    private Level generateLevel(int levelNumber, LabyrinthModel labyrinthModel) {
        return new Level(service.getTitle(levelNumber), service.getPersons(levelNumber, labyrinthModel), service.getSound(levelNumber), service.getBackground(levelNumber));
    }

    public List<Pair<Level, LabyrinthModel>> getPairLevelAndLabyrinth() {
        return pairLevelAndLabyrinth;
    }

    public int getNumberOfLevel() {
        return numberOfLevel;
    }

    @Override
    public String toString() {
        return "InitGame{" +
                "pairLevelAndLabyrinth=" + pairLevelAndLabyrinth +
                ", numberOfLevel=" + numberOfLevel +
                '}';
    }
}