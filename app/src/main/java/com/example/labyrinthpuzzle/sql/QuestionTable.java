package com.example.labyrinthpuzzle.sql;

public class QuestionTable {
    public static final String QUESTION_TABLE = "question";

    public static final String COLUMN_ID = "id_question";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWERS = "answers";
    public static final String COLUMN_RIGHT_ANSWER = "right_answer";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_IMAGES = "images";
    public static final String COLUMN_SOUNDS = "sounds";

    public static String getDropQuery() {
        return "DROP TABLE IF EXISTS " + QUESTION_TABLE;
    }

    public static String getCreateQuery() {
        return "CREATE TABLE IF NOT EXISTS " + QUESTION_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION + " TEXT NULL, " +
                COLUMN_ANSWERS + " TEXT NULL, " +
                COLUMN_RIGHT_ANSWER + " INTEGER NULL, " +
                COLUMN_TYPE + " VARCHAR(45) NULL," +
                COLUMN_LINK + " TEXT NULL," +
                COLUMN_IMAGES + " VARCHAR(45) NULL," +
                COLUMN_SOUNDS + " VARCHAR(45) NULL);";
    }

    public static String[] getAllColumn() {
        return new String[]{
                COLUMN_ID,
                COLUMN_QUESTION,
                COLUMN_ANSWERS,
                COLUMN_TYPE,
                COLUMN_LINK,
                COLUMN_IMAGES,
                COLUMN_SOUNDS
        };
    }
}
