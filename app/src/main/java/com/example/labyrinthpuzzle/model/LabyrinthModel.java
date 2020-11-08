package com.example.labyrinthpuzzle.model;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LabyrinthModel {
    public static final String WHITE = "\033[0;37m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    private final Random random;
    private final int width;
    private final int height;
    private final Position position;
    private int[] labyrinth;


    public LabyrinthModel(final int size) {
        this.width = size;
        this.height = size;
        this.random = new Random();
        this.position = new Position(0, 0);
    }

    /*public static void main(final String[] args) {
        int size = 11;
        final LabyrinthModel labyrinth = new LabyrinthModel(size, size);
        labyrinth.generate();
        //labyrinth.shortestPath(9, 1, 1, 9);
        labyrinth.print();
    }*/

    public int[] generate() {
        this.labyrinth = new int[width * height];
        int sw = (width - 1) / 2, sh = (height - 1) / 2, toGo = sw * sh - 1, mx, my, d, k = 0;

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                labyrinth[i * width + j] = isOdd(i) && isOdd(j) ? k++ : -1;
            }
        }

        while (toGo > 0) {
            do {
                if (isOdd(rand())) {
                    mx = 1 + 2 * (rand() % sw);
                    my = 2 * (1 + (rand() % (sh - 1)));
                } else {
                    my = 1 + 2 * (rand() % sh);
                    mx = 2 * (1 + (rand() % (sw - 1)));
                }
            } while (labyrinth[my * width + mx] != -1);

            if (isOdd(mx)) {
                d = labyrinth[(my - 1) * width + mx] - labyrinth[(my + 1) * width + mx];
                if (d > 0) {
                    labyrinth[my * width + mx] = labyrinth[(my + 1) * width + mx];
                    toGo = propagate(labyrinth[my * width + mx], mx, my - 1, toGo);
                } else if (d < 0) {
                    labyrinth[my * width + mx] = labyrinth[(my - 1) * width + mx];
                    toGo = propagate(labyrinth[my * width + mx], mx, my + 1, toGo);
                }
            } else {
                d = labyrinth[my * width + mx - 1] - labyrinth[my * width + mx + 1];
                if (d > 0) {
                    labyrinth[my * width + mx] = labyrinth[my * width + mx + 1];
                    toGo = propagate(labyrinth[my * width + mx], mx - 1, my, toGo);
                } else if (d < 0) {
                    labyrinth[my * width + mx] = labyrinth[my * width + mx - 1];
                    toGo = propagate(labyrinth[my * width + mx], mx + 1, my, toGo);
                }
            }
        }
        int x, y;
        do {
            x = rand() % width;
            y = rand() % height;
        } while (labyrinth[y * width + x] == -1);
        position.setY(y);
        position.setX(x);
        //System.out.println(x + "           " + y + "             " + labyrinth[y * width + x]);
        labyrinth[y * width + x] = 7;

        return labyrinth;
    }

    private int propagate(final int v, final int x, final int y, int n) {
        final int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        if (v < 0) {
            return n;
        }

        if (v == 0 && isOdd(x) && isOdd(y)) {
            --n;
        }

        labyrinth[y * width + x] = v;
        for (int i = 0; i < 4; ++i) {
            if (labyrinth[(y + dir[i][1]) * width + x + dir[i][0]] > v) {
                n = propagate(v, x + dir[i][0], y + dir[i][1], n);
            }
        }
        return n;
    }

    private int[] subShortestPath(int[] l, int v, final int x0, final int y0, final int x1, final int y1) {
        int i;
        int[][] d = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        l[y0 * width + x0] = v++;
        if (x0 == x1 && y0 == y1) {
            return l;
        }
        for (i = 0; i < 4; ++i) {
            int nx = x0 + d[i][0];
            int ny = y0 + d[i][1];
            if (l[ny * width + nx] == 0 || l[ny * width + nx] > v) {
                l = subShortestPath(l, v, nx, ny, x1, y1);
            }
        }
        return l;
    }

    public void shortestPath(final int x0, final int y0, int x1, int y1) {
        int[] copy = Arrays.copyOf(labyrinth, labyrinth.length);
        int[][] d = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        copy = subShortestPath(copy, 1, x0, y0, x1, y1);
        Stack<Integer> stack = new Stack<>();

        while (x1 != x0 || y1 != y0) {
            stack.push(y1);
            stack.push(x1);
            for (int i = 0; i < 4; ++i) {
                int nx = x1 + d[i][0];
                int ny = y1 + d[i][1];
                if (copy[ny * width + nx] == copy[y1 * width + x1] - 1) {
                    x1 = nx;
                    y1 = ny;
                    break;
                }
            }
        }
        while (!stack.empty()) {
            int x, y;
            x = stack.pop();
            y = stack.pop();
            labyrinth[y * width + x] = 8;
        }
    }

    public void clean() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (labyrinth[i * width + j] == 8) {
                    labyrinth[i * width + j] = 0;
                }
            }
        }
    }

    public int[] getLabyrinth() {
        return labyrinth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return width * height;
    }

    private void print(final Object object) {
        System.out.print(object);
    }

    private int rand() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private boolean isOdd(final int value) {
        return (value & 1) == 1;
    }

    public boolean isRoad(final int x, final int y) {
        return labyrinth[x * width + y] == 0;
    }

    public boolean isWall(final int x, final int y) {
        return labyrinth[x * width + y] == -1;
    }

    public boolean isValidateRoad(final int x, final int y) {
        return labyrinth[x * width + y] == 8;
    }

    public void print() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int value = labyrinth[i * width + j];
                if (value == 0) {
                    str.append(" ");
                }
                if (value == -1) {
                    str.append(WHITE_BACKGROUND + " " + WHITE);
                }
                if (value == 8) {
                    str.append(GREEN_BACKGROUND + " " + WHITE);
                }
            }
            str.append("\n");
        }
        str.append("\n");
        print(str.toString());
    }

    public void moveVertical(final int step, final int direction) {
        int y = direction == 1 ? max(position.getY() + step, 0) : min(position.getY() + 1, height - 1);
        int next_position = y * width + position.getX();
        if (labyrinth[next_position] != -1) {
            int lastPosition = position.getY() * width + position.getX();
            labyrinth[lastPosition] = 0;
            labyrinth[next_position] = 7;
            position.setY(y);
        }
    }

    public void moveHorizontal(final int step, final int direction) {
        int x = direction == 1 ? min(position.getX() + step, width - 1) : max(position.getX() - 1, 0);
        int next_position = position.getY() * width + x;
        if (labyrinth[next_position] != -1) {
            int lastPosition = position.getY() * width + position.getX();
            labyrinth[lastPosition] = 0;
            labyrinth[next_position] = 7;
            position.setX(x);
        }
    }

    public boolean isPosition(int r, int c) {
        return labyrinth[r * width + c] == 7;
    }

    public class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(final int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(final int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
