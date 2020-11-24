package com.example.labyrinthpuzzle.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.labyrinthpuzzle.api.LevelApi;
import com.example.labyrinthpuzzle.api.QuestionApi;
import com.example.labyrinthpuzzle.model.question.QuestionBasic;
import com.example.labyrinthpuzzle.sql.SurvivorDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.R)
public class InitGame {
    List<Level> levels;
    int numberOfLevel = 2;
    SurvivorDAO survivorDAO;

    int index;
    int size = 17;
    Random random = new Random();

    public InitGame(final SurvivorDAO survivorDAO) {
        this.survivorDAO = survivorDAO;
        this.survivorDAO.upgrade();
        this.levels = generateLevels();
        this.index = -1;
    }

    private List<Level> generateLevels() {
        List<Level> levels = new ArrayList<>();
        for (int levelNumber = 1; levelNumber <= numberOfLevel; levelNumber++) {
            levels.add(generateLevel(levelNumber));
        }
        return levels;
    }

    private Level generateLevel(final int levelNumber) {
        LevelApi level = survivorDAO.getLevel(levelNumber);
        System.out.println(level);
        LabyrinthModel labyrinth = new LabyrinthModel(size).generate();
        size += 2;
        return new Level(level.getName(), level.getDescription(), getPersons(level.getQuestions(), labyrinth), level.getSound(), level.getImage(), labyrinth);
    }

    public Level getLevel() {
        index = (index + 1) % numberOfLevel;
        return levels.get(index);
    }

    private Position generatePosition(final LabyrinthModel labyrinthModel) {
        int r, c;
        do {
            r = random.nextInt(labyrinthModel.getHeight());
            c = random.nextInt(labyrinthModel.getWidth());
        } while (labyrinthModel.isWall(r, c));
        return new Position(r, c);
    }

    public List<Person> getPersons(final List<QuestionApi> questions, final LabyrinthModel labyrinthModel) {
        return questions.stream()
                .map(toPerson(labyrinthModel))
                .collect(Collectors.toList());
    }

    private Function<QuestionApi, Person> toPerson(final LabyrinthModel labyrinthModel) {
        return questionApi -> {
            Position position = generatePosition(labyrinthModel);
            return new Person(new QuestionBasic(questionApi.getQuestion(), questionApi.getAnswers(), questionApi.getRightAnswer()))
                    .setRow(position.getRow())
                    .setColumn(position.getColumn());
        };
    }

    @Override
    public String toString() {
        return "InitGame{" +
                "pairLevelAndLabyrinth=" + levels +
                ", numberOfLevel=" + numberOfLevel +
                '}';
    }
}