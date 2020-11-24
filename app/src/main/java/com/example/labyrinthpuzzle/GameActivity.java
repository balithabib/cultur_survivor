package com.example.labyrinthpuzzle;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labyrinthpuzzle.model.InitGame;
import com.example.labyrinthpuzzle.model.Level;
import com.example.labyrinthpuzzle.model.Person;
import com.example.labyrinthpuzzle.model.question.QuestionBasic;
import com.example.labyrinthpuzzle.sql.SurvivorDAO;
import com.example.labyrinthpuzzle.view.LabyrinthView;

import static java.util.Objects.nonNull;

@RequiresApi(api = Build.VERSION_CODES.R)
public class GameActivity extends AppCompatActivity {

    private LabyrinthView labyrinthView;
    private InitGame initGame;
    private Dialog dialogQuestion, dialogDescription;
    private Chronometer chronometer;
    private TextView score;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth);
        labyrinthView = findViewById(R.id.labyrinth_view_id);
        dialogQuestion = new Dialog(this);
        dialogDescription = new Dialog(this);
        chronometer = findViewById(R.id.chrono);
        score = findViewById(R.id.score);
        // init game creation lab and level
        initGame = new InitGame(new SurvivorDAO(this));
        startLevel();
    }

    private void startLevel() {
        Level level = initGame.getLevel();
        score.setText(String.format("0 / %d", level.getNumbersOfPeople()));
        chronometer.start();
        showPopupDescription(level.getDescription());
        //print title of level
        TextView titleView = findViewById(R.id.level_id_title);
        titleView.setText(level.getTitle());

        // print persons
        labyrinthView.setPersons(level.getPersons());

        // print labyrinth
        labyrinthView.setLabyrinthModel(level.getLabyrinth());
    }

    public void move(final View view) {
        int id = ((TextView) view).getId();
        switch (id) {
            case R.id.up:
                labyrinthView.moveUp();
                break;
            case R.id.down:
                labyrinthView.moveDown();
                break;
            case R.id.left:
                labyrinthView.moveLeft();
                break;
            case R.id.right:
                labyrinthView.moveRight();
                break;
        }

        Person person = labyrinthView.findPerson();
        if (nonNull(person)) {
            showPopupQuestion(person);
            labyrinthView.removePerson(person);
            score.setText(labyrinthView.getNumberOfPeopleMet() + " /" + score.getText().toString().split("/")[1]);
            if (labyrinthView.endLevel()) {
                chronometer.stop();
                startLevel();
            }
        }
    }

    public void showPopupQuestion(final Person person) {
        System.out.println(person);
        QuestionBasic question = person.getQuestion();

        dialogQuestion.setContentView(R.layout.pop);

        TextView textQuestion = dialogQuestion.findViewById(R.id.question);
        textQuestion.setText(question.getQuestion());

        LinearLayout answers = dialogQuestion.findViewById(R.id.answers);
        question.getAnswers().forEach(answer -> {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(answer);
            answers.addView(checkBox);
        });

        TextView textClose = dialogQuestion.findViewById(R.id.txtclose);
        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialogQuestion.dismiss();
            }
        });

        dialogQuestion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogQuestion.show();
    }

    public void showPopupDescription(final String description) {
        dialogDescription.setContentView(R.layout.description);

        TextView textDescription = dialogDescription.findViewById(R.id.description);
        textDescription.setText(getString(R.string.description1) + "\n\n\n" + description + "\n\n" + getString(R.string.description2));

        TextView textClose = dialogDescription.findViewById(R.id.txtclose);
        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialogDescription.dismiss();
            }
        });

        ImageView imageView = dialogDescription.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialogDescription.dismiss();
            }
        });

        dialogDescription.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDescription.show();
    }


}
