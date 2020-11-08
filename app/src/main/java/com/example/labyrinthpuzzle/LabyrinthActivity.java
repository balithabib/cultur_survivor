package com.example.labyrinthpuzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labyrinthpuzzle.view.LabyrinthView;

public class LabyrinthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private LabyrinthView labyrinthView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth);
        labyrinthView = findViewById(R.id.labyrinth_view_id);

        int size = 501;
        String[] tab = new String[size];
        for (int i = 1; i < size; ++i) {
            if ((i & 0x01) == 1) {
                tab[i] = String.valueOf(i);
            }
        }


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

    }


    public void move(View view) {
        int id = ((TextView) view).getId();
        switch (id){
            case R.id.up :
                labyrinthView.moveUp();
                break;
            case R.id.down :
                labyrinthView.moveDown();
                break;
            case R.id.left :
                labyrinthView.moveLeft();
                break;
            case R.id.right :
                labyrinthView.moveRight();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence charSequence = (CharSequence) parent.getItemAtPosition(position);
        int size = Integer.parseInt(charSequence.toString());
        labyrinthView.setSize(size);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}