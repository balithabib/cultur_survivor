package com.example.labyrinthpuzzle.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.labyrinthpuzzle.api.LevelApi;
import com.example.labyrinthpuzzle.api.Levels;
import com.example.labyrinthpuzzle.api.QuestionApi;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_DESCRIPTION;
import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_IMAGE;
import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_QUESTIONS;
import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_SOUND;
import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_STATUS;
import static com.example.labyrinthpuzzle.sql.LevelTable.COLUMN_TITLE;
import static com.example.labyrinthpuzzle.sql.LevelTable.LEVEL_TABLE;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_ANSWERS;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_IMAGES;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_LINK;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_QUESTION;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_RIGHT_ANSWER;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_SOUNDS;
import static com.example.labyrinthpuzzle.sql.QuestionTable.COLUMN_TYPE;
import static com.example.labyrinthpuzzle.sql.QuestionTable.QUESTION_TABLE;
import static com.example.labyrinthpuzzle.sql.StaticObjectMapper.STATIC_OBJECT_MAPPER;
import static java.util.stream.Collectors.toList;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SurvivorDAO {

    private final MySQLiteHelper dbHelper;
    private SQLiteDatabase database;


    public SurvivorDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database.close();
    }

    public void upgrade() {
        open();
        dbHelper.onUpgrade(database, database.getVersion(), database.getVersion() + 1);
        init();
    }

    public void init() {
        List<LevelApi> levels = toLevel();
        levels.forEach(this::insertLevel);
    }

    private void insertLevel(LevelApi level) {
        System.out.println(level);
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, level.getName());
        values.put(COLUMN_DESCRIPTION, level.getDescription());
        values.put(COLUMN_IMAGE, level.getImage());
        values.put(COLUMN_SOUND, level.getSound());
        values.put(COLUMN_STATUS, false);
        List<Long> ids = insertQuestions(level.getQuestions());
        System.out.println(ids);
        values.put(COLUMN_QUESTIONS, listToString(ids));
        long insert = database.insert(LEVEL_TABLE, null, values);
        System.out.println("----->" + insert);
    }

    private List<Long> insertQuestions(List<QuestionApi> questions) {
        return questions.stream()
                .map(this::insertQuestion)
                .collect(toList());
    }

    private long insertQuestion(QuestionApi questionApi) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, questionApi.getQuestion());
        values.put(COLUMN_ANSWERS, listToString(questionApi.getAnswers()));
        values.put(COLUMN_RIGHT_ANSWER, questionApi.getRightAnswer());
        values.put(COLUMN_TYPE, "");
        values.put(COLUMN_LINK, "");
        values.put(COLUMN_IMAGES, "");
        values.put(COLUMN_SOUNDS, "");
        return database.insert(QUESTION_TABLE, null, values);
    }

    private List<LevelApi> toLevel() {
        try {
            return STATIC_OBJECT_MAPPER.readValue(SurvivorDAO.class.getResourceAsStream("/res/raw/questions.json"), Levels.class).getLevels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public LevelApi getLevel(long id) {
        System.out.println("------------------------------- " + id);
        return getEntity(
                LEVEL_TABLE,
                LevelTable.getAllColumn(),
                LevelTable.COLUMN_ID + " = " + id,
                this::cursorToLevelApi
        );
    }

    private List<QuestionApi> getQuestionsApi(String questionIds) {
        List<String> ids = stringToList(questionIds);
        return ids.stream()
                .map(this::getQuestionApi)
                .collect(toList());
    }

    private QuestionApi getQuestionApi(String id) {
        return getEntity(
                QUESTION_TABLE,
                QuestionTable.getAllColumn(),
                QuestionTable.COLUMN_ID + " = " + id,
                this::cursorToQuestionApi);
    }

    private <T> T getEntity(final String table, final String[] columns, final String selection, final Function<Cursor, T> extractFunction) {
        Cursor cursor = database.query(table, columns, selection, null, null, null, null);
        cursor.moveToFirst();

        T entity = extractFunction.apply(cursor);
        cursor.close();

        return entity;
    }

    private LevelApi cursorToLevelApi(Cursor cursor) {
        return new LevelApi(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                getQuestionsApi(cursor.getString(6))
        );
    }

    private QuestionApi cursorToQuestionApi(Cursor cursor) {
        return new QuestionApi(
                cursor.getString(1),
                stringToList(cursor.getString(2)),
                cursor.getInt(3)
        );
    }

    private List<String> stringToList(String elements) {
        return Arrays.asList(elements.split("\\|"));
    }

    private <T> String listToString(List<T> elements) {
        return StringUtils.join(elements, "|");
    }
}