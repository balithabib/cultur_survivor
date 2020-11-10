package com.example.labyrinthpuzzle.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.labyrinthpuzzle.model.LabyrinthModel;
import com.example.labyrinthpuzzle.model.Person;
import com.example.labyrinthpuzzle.model.QuestionBasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Service {
    Random random = new Random();
    List<String> tittles = List.of(
            "La peste d’Athènes (-430 à -426 avant J.C)",
            "La peste Antonine (165-166)",
            "La peste noire (1347-1352)",
            "La grippe espagnole (1918-1919)",
            "Le choléra (1926-1832)",
            "La grippe asiatique (1956-1957)",
            "Le sida (1981-aujourd’hui)",
            "Coronavirus (2019- aujourd’hui)"
    );
    List<List<Person>> people = List.of(
            List.of(
                    new Person(new QuestionBasic("1 ?", List.of("p1", "p2", "p3"), 1))
            ),
            List.of(
                    new Person(new QuestionBasic("2 ?", List.of("p1", "p2", "p3"), 1)),
                    new Person(new QuestionBasic("3 ?", List.of("p1", "p2", "p3"), 1))
            ),
            List.of(
                    new Person(new QuestionBasic("4 ?", List.of("p1", "p2", "p3"), 1)),
                    new Person(new QuestionBasic("5 ?", List.of("p1", "p2", "p3"), 1)),
                    new Person(new QuestionBasic("6 ?", List.of("p1", "p2", "p3"), 1))
            ),
            List.of(),
            List.of(),
            List.of(),
            List.of(),
            List.of()
    );

    List<List<Integer>> getPositions(LabyrinthModel labyrinthModel, int size) {
        List<List<Integer>> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int x, y;
            do {
                x = random.nextInt(13);
                y = random.nextInt(13);
                System.out.println(x + "  " + y);
            } while (labyrinthModel.isRoad(x, y));
            positions.add(List.of(x, y));
        }
        return positions;
    }

    public String getTitle(int levelNumber) {
        return tittles.get(levelNumber);
    }

    public List<Person> getPersons(int levelNumber, LabyrinthModel labyrinthModel) {
        List<Person> peopleBis = people.get(levelNumber);
        List<List<Integer>> positions = getPositions(labyrinthModel, peopleBis.size());
        List<Person> pp = new ArrayList<>();
        for (int i = 0; i < peopleBis.size(); i++) {
            pp.add(peopleBis.get(i).setX(positions.get(i).get(0)).setY(positions.get(i).get(0)));
        }

        return pp;
    }

    public String getSound(int levelNumber) {
        return null;
    }

    public String getBackground(int levelNumber) {
        return null;
    }
}
