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

class Vector3 {
    double x;
    double y;
    double z;
    Vector3() { }
    Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3(Vector3 p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}

// TODO: get mouse position
// TODO EasyApp


// TODO: mouse clicked
// TODO: mouse released

// TODO: bare bones Paint starter (just one stroke forever)
class Paint extends App {
    public static void main(String[] args) { new Paint().startGameLoop(); }

    ArrayList<ArrayList<Vector2>> strokes;
    ArrayList<Vector3> colors;

    @Override
    void initalizeOrReset() {
        strokes = new ArrayList<ArrayList<Vector2>>();
        colors = new ArrayList<Vector3>();
    }

    @Override
    void updateAndDraw() {
        if (mousePressed) {
            strokes.add(new ArrayList<Vector2>());
            colors.add(new Vector3(Cow.randomDouble(), Cow.randomDouble(), Cow.randomDouble()));
        }
        if (mouseHeld) { strokes.get(strokes.size() - 2).add(mousePosition); }

        // TODO choose windowCenterInWorldUnits

        if (keyPressed('X')) {
            for (int i = 0; i < strokes.size(); ++i) {
                for (int j = 0; j < strokes.get(i).size(); ++j) {
                    strokes.get(i).get(j).x *= -1;
                }
            }
        }

        for (int i = 0; i < strokes.size(); ++i) {
            drawLineStrip(strokes.get(i), colors.get(i));
        }

        {
            Vector2[] array = { new Vector2(0.1, 0.1), new Vector2(0.8, 0.8) };
            drawLineStrip(array, new Vector3(1.0, 0.0, 0.0));
        }
    }
}

class App extends JPanel {
    // TODO: Only expose world coordinates to the user.
    // TODO: Only have Vector2 versions of draw functions.

    // TODO: non-pixel coordinate system with reasonable center


    // // read only app state
    // mouse
    Vector2 mousePosition; // world coordinates
    // window
    int _windowHeightInPixels;
    int _windowWidthInPixels;
    // TODO Vector2?
    double _windowWidthInWorldUnits;
    double _windowHeightInWorldUnits;
    double _windowCenterXInWorldUnits;
    double _windowCenterYInWorldUnits;
    double _windowAspectRatio() { return ((double) _windowHeightInPixels) / _windowHeightInPixels; }
    double _windowWidthInWorldUnits() { return _windowAspectRatio() * _windowHeightInWorldUnits; }
    double _windowPixelsPerWorldUnits() { return _windowHeightInPixels / _windowHeightInWorldUnits; }
    Vector2 _windowSizeInWorldUnits() { return new Vector2(_windowHeightInWorldUnits, _windowWidthInWorldUnits()); }
    Vector2 _windowPixelFromWorld(Vector2 sWorld) {
        Vector2 sPixel = new Vector2();
        double scale = _windowPixelsPerWorldUnits();
        sPixel.x = (int) (scale *                             ((sWorld.x - (_windowCenterXInWorldUnits - .5 *  _windowWidthInWorldUnits))));
        sPixel.y = (int) (scale * (_windowHeightInWorldUnits - (sWorld.y - (_windowCenterYInWorldUnits - .5 * _windowHeightInWorldUnits))));
        return sPixel;
    }
    Vector2 _windowWorldFromPixel(Vector2 sPixel) {
        Vector2 sWorld = new Vector2();
        double scale = _windowPixelsPerWorldUnits();
        sWorld.x =                             (sPixel.x / scale) + (_windowCenterXInWorldUnits - .5 *  _windowWidthInWorldUnits);
        sWorld.y = _windowHeightInWorldUnits - (sPixel.y / scale) + (_windowCenterYInWorldUnits - .5 * _windowHeightInWorldUnits);
        return sWorld;
    }


    // TODO: windowCenter
    // TODO: formalize transforms (DRY)

    // // _graphics library
    // set color
    void _graphicsSetColor(Vector3 color) {
        _graphics.setColor(new Color((float) color.x, (float) color.y, (float) color.z));
    }
    // line strip
    void drawLineStrip(Collection<Vector2> points, Vector3 color) { drawLineStrip(points.toArray(new Vector2[0]), color); }
    void drawLineStrip(Vector2[] points, Vector3 color) {
        _graphicsSetColor(color);
        int nPoints = points.length;
        int[] xPoints = new int[nPoints];
        int[] yPoints = new int[nPoints];

        for (int i = 0; i < nPoints; ++i) {
            Vector2 tmp = _windowPixelFromWorld(points[i]);
            xPoints[i] = (int) tmp.x;
            yPoints[i] = (int) tmp.y;
        }

        _graphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    // // corner rectangle
    // void drawCornerRectangle(double xCornerA, double yCornerA, double xCornerB, double yCornerB, Vector3 color) {
    //     _graphicsSetColor(color);

    //     // PixelFromWorld
    //     yCornerA = _windowHeightInWorldUnits - yCornerA;
    //     yCornerB = _windowHeightInWorldUnits - yCornerB;
    //     double scale = _windowHeightInPixels / _windowHeightInWorldUnits;
    //     xCornerA *= scale;
    //     yCornerA *= scale;
    //     xCornerB *= scale;
    //     yCornerB *= scale;

    //     {
    //         // swap if necessary to make A lower-left and B upper-right
    //         if (xCornerA > xCornerB) { double tmp = xCornerA; xCornerA = xCornerB; xCornerB = tmp; }
    //         if (yCornerA > yCornerB) { double tmp = yCornerA; yCornerA = yCornerB; yCornerB = tmp; }

    //         _graphics.fillRect(
    //                 (int) (xCornerA),
    //                 (int) (yCornerA),
    //                 (int) (xCornerB - xCornerA),
    //                 (int) (yCornerB - yCornerA));
    //     }
    // }
    // void drawCornerRectangle(Vector2 cornerA, Vector2 cornerB, Vector3 color) {
    //     drawCornerRectangle(cornerA.x, cornerA.y, cornerB.x, cornerB.y, color);
    // }




    JFrame jFrame;
    boolean mousePressed = false;
    boolean mouseHeld = false;
    boolean mouseReleased = false;
    HashMap<Integer, Boolean> _keyPressed = new HashMap<>();
    HashMap<Integer, Boolean> _keyHeld = new HashMap<>();
    HashMap<Integer, Boolean> _keyReleased = new HashMap<>();
    boolean keyHeld(int key) { return _keyHeld.getOrDefault(key, false); }
    boolean keyPressed(int key) {
        return _keyPressed.getOrDefault(key, false);
    }
    boolean keyReleased(int key) { return _keyReleased.getOrDefault(key, false); }


    App() {
        super();

        {
            this.addMouseListener( new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mousePressed = true;
                    mouseHeld = true;
                }

                @Override public void mouseReleased(MouseEvent e) {
                mouseHeld = false;
                mouseReleased = true;
                }
            });

            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
                synchronized (App.class) {
                    int key = event.getKeyCode();
                    if (event.getID() == KeyEvent.KEY_PRESSED) {
                        if (!keyHeld(key)) {
                            _keyPressed.put(key, true);
                        }
                        _keyHeld.put(key, true);
                    }
                    if (event.getID() == KeyEvent.KEY_RELEASED) {
                        _keyReleased.put(key, true);
                        _keyHeld.put(key, false);
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
    void startGameLoop() { this.startGameLoop(2, 2, 0, 0, 512); }
    void startGameLoop(double _windowWidthInWorldUnits, double _windowHeightInWorldUnits, double _windowCenterXInWorldUnits,double _windowCenterYInWorldUnits, int windowHeightInPixels) {

        this.setBackground(Color.BLACK);

        this._windowWidthInWorldUnits = _windowWidthInWorldUnits;
        this._windowHeightInWorldUnits = _windowHeightInWorldUnits;
        this._windowCenterXInWorldUnits = _windowCenterXInWorldUnits;
        this._windowCenterYInWorldUnits = _windowCenterYInWorldUnits;
        this.jFrame.setSize((int) (_windowWidthInWorldUnits / _windowHeightInWorldUnits * windowHeightInPixels), (int) (windowHeightInPixels));
        jFrame.setVisible(true);


        while (!keyHeld('Q')) {
            this.repaint();
            try { Thread.sleep(1000 / 60); } catch (Exception e) { }
        }
        System.exit(0);
    }

    boolean _initialized = false;
    void initalizeOrReset() { }

    void updateAndDraw() {}
    Graphics _graphics;

    @Override 
    public void paintComponent(Graphics _graphics) {
        // NOTE: try-catch to actually kill the app on an error
        try {
            super.paintComponent(_graphics);
            this._graphics = _graphics; {
                Rectangle rectangle = jFrame.getBounds();
                _windowHeightInPixels = rectangle.height;
                _windowWidthInPixels = rectangle.width;
            }

            {
                Point point;
                {
                    point = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(point, this);
                }
                this.mousePosition = _windowWorldFromPixel(new Vector2(point.x, point.y));
            }

            if (!_initialized || keyPressed('R')) {
                _initialized = true;
                initalizeOrReset();

                mousePressed = false;
                mouseHeld = false;
                mouseReleased = false;
                _keyPressed.clear();
                _keyHeld.clear();
                _keyReleased.clear();
            }

            updateAndDraw();

            { // end of jFrame
                mousePressed = false;
                mouseReleased = false;
                _keyPressed.clear();
                _keyReleased.clear();
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new App().startGameLoop();
    }
}


// TODO: EasyApp
// // reset button, time, frame



