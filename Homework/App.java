import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.event.*;

import java.util.*;
import java.util.Arrays;

import javax.swing.*;

import java.lang.Math;


class Vector2 {
    double x;
    double y;
    Vector2() {} // NOTE: x and y automatically initialized to zero
    Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Vector2(Vector2 p) {
        this.x = p.x;
        this.y = p.y;
    }
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    // NOTE: These are nice
    // TODO: pythagorean theorem question
    Vector2 plus(Vector2 other) { return new Vector2(this.x + other.x, this.y + other.y); }
    Vector2 minus(Vector2 other) { return new Vector2(this.x - other.x, this.y - other.y); }
    Vector2 times(double scalar) { return new Vector2(scalar * this.x, scalar * this.y); }
    Vector2 dividedBy(double scalar) { return this.times(1.0 / scalar); }
    double squaredNorm() { return this.x * this.x + this.y + this.y; }
    double norm() { return Math.sqrt(this.squaredNorm()); }
    Vector2 normalized() { return this.dividedBy(this.norm()); }

    static double distance(Vector2 a, Vector2 b) { return (a.minus(b)).norm(); }
    // HW: static Vector2 distance
}


class Test {
    public static void main(String[] args) {
        ToyArrayList<Vector2> points = new ToyArrayList<Vector2>();
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
        points.add(new Vector2(1.0, 1.0));
    }
}


// TODO: get mouse position


// TODO EasyApp

class BootlegPaint extends App {
    public static void main(String[] args) {
        new BootlegPaint().startGameLoop();
    }

    boolean initialized;
    ArrayList<Vector2> list;
    Vector2[] array = { new Vector2(0.1, 0.1), new Vector2(0.8, 0.8) };

    void updateAndDraw() {
        if (!initialized || key_pressed('R')) {
            initialized = true;
            list = new ArrayList<Vector2>();
        }

        list.add(this.mousePosition);
        drawLineStrip(list, new Color(0.5f, 0.9f, 0.2f));
        drawLineStrip(array, Color.RED);
    }

}

class App extends JPanel {
    // TODO: Only expose world coordinates to the user.
    // TODO: Only have Vector2 versions of draw functions.

    // TODO: non-pixel coordinate system with reasonable center



    // read only app state
    Vector2 mousePosition;



    int _windowHeightInPixels; // NOTE: these are set automatically at the beginning of each frame
    int _windowWidthInPixels;  // NOTE: these are set automatically at the beginning of each frame

    double windowWidthInWorldUnits;
    double windowHeightInWorldUnits;
    double _getWindowAspectRatio() { return ((double) _windowHeightInPixels) / _windowHeightInPixels; }
    double getWindowWidthInWorldUnits() { return _getWindowAspectRatio() * windowHeightInWorldUnits; }
    Vector2 getWindowSizeInWorldUnits() { return new Vector2(windowHeightInWorldUnits, getWindowWidthInWorldUnits()); }


    void drawLineStrip(Vector2[] points, Color color) {
        graphics.setColor(color);
        double scale = _windowHeightInPixels / windowHeightInWorldUnits;
        int nPoints = points.length;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];
        for (int i = 0; i < nPoints; ++i) {
            xPoints[i] = (int) (scale * points[i].x);
            yPoints[i] = (int) (scale * (windowHeightInWorldUnits - points[i].y));
        }
        graphics.drawPolyline(xPoints, yPoints, nPoints);
    }
    void drawLineStrip(List<Vector2> points, Color color) {
        drawLineStrip(points.toArray(new Vector2[0]), color);
    }





    void drawCornerRectangle(double xCornerA, double yCornerA, double xCornerB, double yCornerB, Color color) {
        graphics.setColor(color);

        // change of coordinates
        yCornerA = windowHeightInWorldUnits - yCornerA;
        yCornerB = windowHeightInWorldUnits - yCornerB;
        double scale = _windowHeightInPixels / windowHeightInWorldUnits;
        xCornerA *= scale;
        yCornerA *= scale;
        xCornerB *= scale;
        yCornerB *= scale;

        // swap if necessary to make A lower-left and B upper-right
        if (xCornerA > xCornerB) { double tmp = xCornerA; xCornerA = xCornerB; xCornerB = tmp; }
        if (yCornerA > yCornerB) { double tmp = yCornerA; yCornerA = yCornerB; yCornerB = tmp; }

        graphics.fillRect(
                (int) (xCornerA),
                (int) (yCornerA),
                (int) (xCornerB - xCornerA),
                (int) (yCornerB - yCornerA));
    }
    void drawCornerRectangle(Vector2 cornerA, Vector2 cornerB, Color color) {
        drawCornerRectangle(cornerA.x, cornerA.y, cornerB.x, cornerB.y, color);
    }



    // TODO: port this
    void drawCenteredSquare(Color color, Vector2 s, Vector2 size) {
        graphics.setColor(color);
        graphics.fillRect((int) (s.x - size.x / 2), (int) (_windowHeightInPixels - s.y - size.y / 2.0), (int) size.x, (int) size.y);
    }




    JFrame jFrame;
    HashMap<Integer, Boolean> _key_held;
    HashMap<Integer, Boolean> _key_pressed;
    HashMap<Integer, Boolean> _key_released;
    boolean key_held(int key) { return _key_held.getOrDefault(key, false); }
    boolean key_pressed(int key) {
        return _key_pressed.getOrDefault(key, false);
    }
    boolean key_released(int key) { return _key_released.getOrDefault(key, false); }

    App() {
        super();
        {
            _key_held = new HashMap<>();
            _key_pressed = new HashMap<>();
            _key_released = new HashMap<>();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
                synchronized (App.class) {
                    int key = event.getKeyCode();
                    if (event.getID() == KeyEvent.KEY_PRESSED) {
                        if (!key_held(key)) {
                            _key_pressed.put(key, true);
                        }
                        _key_held.put(key, true);
                    }
                    if (event.getID() == KeyEvent.KEY_RELEASED) {
                        _key_released.put(key, true);
                        _key_held.put(key, false);
                    }
                    return false;
                }
            });
        }
        {
            jFrame = new JFrame("");
            jFrame.setSize(1000, 500);
            jFrame.setLocation(256, 64); // TODO: expose
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.getContentPane().add(this, BorderLayout.CENTER);
            jFrame.setUndecorated(true);
        }
    }

    // TODO: pixelsPer...
    void startGameLoop() { this.startGameLoop(1, 1, 512); }
    void startGameLoop(double initialWindowWidthInWorldUnits, double initialWindowHeightInWorldUnits, int initialPixelsPerWorldUnit) {

        this.setBackground(Color.GRAY);

        this.windowWidthInWorldUnits = initialWindowWidthInWorldUnits;
        this.windowHeightInWorldUnits = initialWindowHeightInWorldUnits;
        this.jFrame.setSize((int) (initialPixelsPerWorldUnit * initialWindowWidthInWorldUnits), (int) (initialPixelsPerWorldUnit * initialWindowHeightInWorldUnits));
        jFrame.setVisible(true);


        while (!key_held('Q')) {
            this.repaint();
            try { Thread.sleep(1000 / 60); } catch (Exception e) { }
        }
        System.exit(0);
    }
    
    void updateAndDraw() {}
    Graphics graphics;
    @Override public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.graphics = graphics; {
            Rectangle rectangle = jFrame.getBounds();
            _windowHeightInPixels = rectangle.height;
            _windowWidthInPixels = rectangle.width;
        }

        {
            Point point = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(point, this);

            double scale = windowHeightInWorldUnits / _windowHeightInPixels ;
            this.mousePosition = new Vector2(scale * point.x, windowHeightInWorldUnits - (scale * point.y));
        }

        updateAndDraw();

        { // end of jFrame
            _key_pressed.clear();
            _key_released.clear();
        }
    }

    public static void main(String[] args) {
        new App().startGameLoop();
    }
}


// TODO: EasyApp
// // reset button, time, frame



