import java.awt.*;
import java.util.*;
import javax.swing.*;

class Tetris extends App {

    // TODO: arrow keys
    boolean initialized;
    int frame;
    final int gridNumX = 10;
    final int gridNumY = 20;
    boolean[][] grid;
    int xCurr;
    int yCurr;
    boolean bufferedKeyPressA;
    boolean bufferedKeyPressD;


    public static void main(String[] args) {
        System.out.println("A move left");
        System.out.println("D move right");
        System.out.println("S move down fast");
        System.out.println("W move down infinitely fast");
        new Tetris().startGameLoop(10.0, 20.0, 8, 0.0, 0.0, Color.GRAY);
    }
    void doFrame() {
        if (!initialized || key_pressed('R')) {
            initialized = true;
            frame = 0;

            grid = new boolean[gridNumX][gridNumY];

            xCurr = gridNumX / 2;
            yCurr = gridNumY;
            bufferedKeyPressA = false;
            bufferedKeyPressD = false;
        }

        if (key_pressed('A')) { bufferedKeyPressA = true; }
        if (key_pressed('D')) { bufferedKeyPressD = true; }

        boolean moveClockTic = ((frame % 4) == 0);
        boolean subMoveClockTic = ((frame % 2) == 0);

        if (moveClockTic) {
            if (bufferedKeyPressA || key_held('A')) {
                bufferedKeyPressA = false;
                if ((xCurr != 0) && ((yCurr >= gridNumY) || !grid[xCurr - 1][yCurr])) { --xCurr; }
            }
            if (bufferedKeyPressD || key_held('D')) {
                bufferedKeyPressD = false;
                if ((xCurr != (gridNumX - 1)) && ((yCurr >= gridNumY) || !grid[xCurr + 1][yCurr])) { ++xCurr; }
            }
        }

        int numSteps = 0;
        if (moveClockTic || (key_held('S') && subMoveClockTic)) {
            numSteps = 1;
        }
        if (key_pressed('W')) {
            numSteps = gridNumY;
        }

        for (int step = 0; step < numSteps; ++step) {
            if ((yCurr != 0) && !grid[xCurr][yCurr - 1]) {
                --yCurr;
            } else {
                if (yCurr == gridNumY) {
                    initialized = false;
                    return;
                }
                grid[xCurr][yCurr] = true;
                xCurr = gridNumX / 2;
                yCurr = gridNumY;
                break;
            }
        }

        // Monokai.RED

        { // draw
            drawCornerRectangle(xCurr, yCurr, xCurr + 1, yCurr + 1, Color.GREEN);
            for (int x = 0; x < gridNumX; ++x) {
                for (int y = 0; y < gridNumY; ++y) {
                    if (grid[x][y]) {
                        drawCornerRectangle(x, y, x + 1, y + 1, Color.WHITE);
                    }
                }
            }
        }

        ++frame;
    }

}