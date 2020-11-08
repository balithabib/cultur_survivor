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

import java.util.Random;

public class LabyrinthView extends View {
    private int selectedRow = -1, selectedColumn = -1;
    private int rowNow = -1, columnNow = -1;
    private float width, height;
    private Paint wallPaint, roadPaint, roadValidatedPaint, positionPaint;
    private float cellSize;
    private LabyrinthModel labyrinthModel;
    private int status = 0;
    private int[] route = {-1, -1, -1, -1};
    private Random random = new Random();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LabyrinthView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.labyrinthModel = new LabyrinthModel(51);
        this.labyrinthModel.generate();

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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sizePixels, sizePixels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = (float) getWidth();
        height = (float) getHeight();
        cellSize = width / (float) labyrinthModel.getWidth();

        for (int r = 0; r < labyrinthModel.getHeight(); r++) {
            for (int c = 0; c < labyrinthModel.getWidth(); c++) {

                if (labyrinthModel.isWall(r, c)) {
                    fillCell(canvas, r, c, wallPaint, cellSize);
                } else if (r == selectedRow && c == selectedColumn) {
                    fillCell(canvas, r, c, roadValidatedPaint, cellSize);
                } else if (labyrinthModel.isRoad(r, c)) {
                    fillCell(canvas, r, c, roadPaint, 2 * cellSize);
                } else if (labyrinthModel.isValidateRoad(r, c)) {
                    fillCell(canvas, r, c, roadValidatedPaint, cellSize);
                } else if (labyrinthModel.isPosition(r, c)) {
                    fillCell(canvas, r, c, positionPaint, cellSize);
                }
            }
        }
    }

    public void fillCell(Canvas canvas, int r, int c, Paint paint, float cellSize) {
        canvas.drawRect(c * cellSize, r * cellSize, (c + 1) * cellSize, (r + 1) * cellSize, paint);
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

    public void setSize(int size) {
        this.labyrinthModel = new LabyrinthModel(size);
        this.labyrinthModel.generate();
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
}
