package com.example.labyrinthpuzzle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.labyrinthpuzzle.model.LabyrinthModel;
import com.example.labyrinthpuzzle.model.Person;
import com.example.labyrinthpuzzle.model.Position;

import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LabyrinthView extends View {
    private final Paint wallPaint, roadPaint, roadValidatedPaint, positionPaint, personPaint;
    private final int[] route = {-1, -1, -1, -1};
    private int selectedRow = -1, selectedColumn = -1;
    private float cellSize;
    private LabyrinthModel labyrinthModel;
    private int status = 0;
    private List<Person> persons;
    private int numberPeopleMet;

    public LabyrinthView(Context context, AttributeSet attrs) {
        super(context, attrs);

        wallPaint = new Paint();
        wallPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        wallPaint.setColor(Color.rgb(0, 0, 0));

        roadPaint = new Paint();
        roadPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roadPaint.setColor(Color.rgb(255, 255, 255));

        roadValidatedPaint = new Paint();
        roadValidatedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        roadValidatedPaint.setColor(Color.rgb(0, 255, 0));

        positionPaint = new Paint();
        positionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        positionPaint.setColor(Color.rgb(255, 0, 0));

        personPaint = new Paint();
        personPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        personPaint.setColor(Color.rgb(0, 0, 255));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sizePixels, sizePixels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = (float) getWidth();
        cellSize = width / (float) labyrinthModel.getWidth();

        for (int r = 0; r < labyrinthModel.getHeight(); r++) {
            for (int c = 0; c < labyrinthModel.getWidth(); c++) {

                if (labyrinthModel.isWall(r, c)) {
                    fillCell(canvas, r, c, wallPaint, cellSize);
                } else if (r == selectedRow && c == selectedColumn) {
                    fillCell(canvas, r, c, roadValidatedPaint, cellSize);
                } else if (findPersonInThisPosition(r, c).isPresent()) {
                    fillCell(canvas, r, c, personPaint, cellSize);
                    canvas.drawRect(c * cellSize + 5, r * cellSize + 5, (c + 1) * cellSize  -5, (r + 1) * cellSize - 5, personPaint);
                } else if (labyrinthModel.isRoad(r, c)) {
                    fillCell(canvas, r, c, roadPaint, 2 * cellSize);
                } else if (labyrinthModel.isValidateRoad(r, c)) {
                    fillCell(canvas, r, c, roadValidatedPaint, cellSize);
                } else if (labyrinthModel.isPosition(r, c)) {
                    fillCell(canvas, r, c, positionPaint, cellSize);
                }
            }
        }
        //drawColumns(canvas);
        //drawCRows(canvas);
    }

    protected void drawColumns(Canvas canvas) {
        float width = (float) getWidth();
        cellSize = width / (float) labyrinthModel.getWidth();
        int r, c;
        for (c = 1; c < labyrinthModel.getWidth() - 1; c++) {
            int count = 0, start = 0, end = 0;
            for (r = 1; r < labyrinthModel.getHeight() - 1; r++) {
                if (labyrinthModel.isWall(r, c)) {
                    count++;
                } else {
                    if (count > 1) {
                        drawRect(canvas, r, r - count, c, c + 1, positionPaint, cellSize);
                    }
                    count = 0;
                }
            }
            if (count > 1) {
                drawRect(canvas, r, r - count, c, c + 1, positionPaint, cellSize);
            }
        }
    }

    protected void drawCRows(Canvas canvas) {
        float width = (float) getWidth();
        cellSize = width / (float) labyrinthModel.getWidth();
        int r, c;
        for (r = 1; r < labyrinthModel.getHeight() - 1; r++) {
            int count = 0, start = 0, end = 0;
            for (c = 1; c < labyrinthModel.getWidth() - 1; c++) {
                if (labyrinthModel.isWall(r, c)) {
                    count++;
                } else {
                    if (count > 1) {
                        drawRect(canvas, r, r + 1, c, c - count, positionPaint, cellSize);
                    }
                    count = 0;
                }
            }
            if (count > 1) {
                drawRect(canvas, r, r + 1, c, c - count, positionPaint, cellSize * 0.5f);
            }
        }
    }

    private Optional<Person> findPersonInThisPosition(int r, int c) {
        return persons.stream()
                .filter(person -> person.getRow() == r && person.getColumn() == c)
                .findFirst();
    }

    public void fillCell(Canvas canvas, int r, int c, Paint paint, float cellSize) {
        canvas.drawRect(c * cellSize, r * cellSize, (c + 1) * cellSize, (r + 1) * cellSize, paint);
    }

    public void drawRect(Canvas canvas, int r1, int r2, int c1, int c2, Paint paint, float cellSize) {
        canvas.drawRect(c1 * cellSize, r1 * cellSize, c2 * cellSize, r2 * cellSize, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            selectPosition((int) (event.getY() / cellSize), (int) (event.getX() / cellSize));
            invalidate();
            return true;
        }
        return false;
    }

    private void selectPosition(final int row, final int column) {
        if (!labyrinthModel.isWall(row, column)) {
            status++;
            selectedRow = row;
            selectedColumn = column;
            switch (status) {
                case 1:
                    route[0] = selectedColumn;
                    route[1] = selectedRow;
                    break;
                case 2:
                    route[2] = selectedColumn;
                    route[3] = selectedRow;
                    labyrinthModel.shortestPath(route[0], route[1], route[2], route[3]);
                    break;
                default:
                    status = 1;
                    route[0] = selectedColumn;
                    route[1] = selectedRow;
                    labyrinthModel.clean();
                    break;
            }
        }
    }

    public void setLabyrinthModel(final LabyrinthModel labyrinthModel) {
        this.labyrinthModel = labyrinthModel;
        selectedRow = -1;
        selectedColumn = -1;
        status = 0;
        numberPeopleMet = 0;
        invalidate();
    }

    public void moveUp() {
        labyrinthModel.moveVertical(-1, 1);
        invalidate();
    }

    public void moveDown() {
        labyrinthModel.moveVertical(1, -1);
        invalidate();
    }

    public void moveLeft() {
        labyrinthModel.moveHorizontal(-1, -1);
        invalidate();
    }

    public void moveRight() {
        labyrinthModel.moveHorizontal(1, 1);
        invalidate();
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public boolean endLevel() {
        return persons.isEmpty();
    }

    public int getNumberOfPeopleMet() {
        return numberPeopleMet;
    }

    public Person findPerson() {
        Position position = labyrinthModel.getPosition();
        Optional<Person> personInThisPosition = findPersonInThisPosition(position.getRow(), position.getColumn());
        //System.out.println("1--------- --------- --------> " + numberPeopleMet + "    " + persons.size() + "   " + personInThisPosition.isPresent());
        return personInThisPosition.orElse(null);
    }

    public void removePerson(final Person person) {
        numberPeopleMet++;
        persons.remove(person);
    }
}
