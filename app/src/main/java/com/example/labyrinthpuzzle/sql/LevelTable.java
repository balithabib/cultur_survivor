package com.example.labyrinthpuzzle.sql;

public class LevelTable {
    public static final String LEVEL_TABLE = "level";

    public static final String COLUMN_ID = "id_level";
    public static final String COLUMN_TITLE = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_SOUND = "sound";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_QUESTIONS = "questions";

    public static String getDropQuery() {
        return "DROP TABLE IF EXISTS " + LEVEL_TABLE;
    }

    public static String getCreateQuery() {
        return "CREATE TABLE IF NOT EXISTS " + LEVEL_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " VARCHAR(45) NULL, " +
                COLUMN_DESCRIPTION + " TEXT NULL, " +
                COLUMN_IMAGE + " INTEGER NOT NULL, " +
                COLUMN_SOUND + " INTEGER NOT NULL, " +
                COLUMN_STATUS + " BOOLEAN NOT NULL, " +
                COLUMN_QUESTIONS + " TEXT NOT NULL);";
    }

    public static String[] getAllColumn() {
        return new String[]{
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_DESCRIPTION,
                COLUMN_IMAGE,
                COLUMN_SOUND,
                COLUMN_STATUS,
                COLUMN_QUESTIONS
        };
    }
}
