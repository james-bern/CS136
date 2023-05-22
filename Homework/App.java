import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;







class App extends JPanel {
    // TODO: non-pixel coordinate system with reasonable center

    // NOTE: these are set automatically at the beginning of each frame
    int _windowHeightInPixels;
    int _windowWidthInPixels;

    double windowWidth;
    double windowHeight;
    double _getWindowAspectRatio() { return ((double) _windowHeightInPixels) / _windowHeightInPixels; }
    double getWindowWidth() { return _getWindowAspectRatio() * windowHeight; }
    Vector2 getWindowSize() { return new Vector2(windowHeight, getWindowWidth()); }
    Vector2 windowCenter = new Vector2();

    // TODO: test app (constructor, mouse controls for camera, etc.)
    void drawCenteredSquare(Color color, Vector2 s, Vector2 size) {
        this.graphics.setColor(color);
        this.graphics.fillRect((int) (s.x - size.x / 2), (int) (_windowHeightInPixels - s.y - size.y / 2.0), (int) size.x, (int) size.y);
    }

    void drawCornerRectangle(double xCornerA, double yCornerA, double xCornerB, double yCornerB, Color color) {
        this.graphics.setColor(color);

        // change of coordinates
        yCornerA = windowHeight - yCornerA;
        yCornerB = windowHeight - yCornerB;
        double scale = _windowHeightInPixels / windowHeight;
        xCornerA *= scale;
        yCornerA *= scale;
        xCornerB *= scale;
        yCornerB *= scale;

        // swap if necessary to make A lower-left and B upper-right
        if (xCornerA > xCornerB) { double tmp = xCornerA; xCornerA = xCornerB; xCornerB = tmp; }
        if (yCornerA > yCornerB) { double tmp = yCornerA; yCornerA = yCornerB; yCornerB = tmp; }

        this.graphics.fillRect(
                (int) (xCornerA),
                (int) (yCornerA),
                (int) (xCornerB - xCornerA),
                (int) (yCornerB - yCornerA));
    }
    void drawCornerRectangle(Vector2 cornerA, Vector2 cornerB, Color color) {
        drawCornerRectangle(cornerA.x, cornerA.y, cornerB.x, cornerB.y, color);
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
    void startGameLoop() { this.startGameLoop(1000, 500, 1, 0, 0, Color.BLACK); }
    void startGameLoop(double initialWindowWidth, double initialWindowHeight, int initialPixelsPerWorldUnit, double xCenter, double yCenter, Color color) {

        this.setBackground(color);

        this.windowWidth = initialWindowWidth;
        this.windowHeight = initialWindowHeight;
        this.jFrame.setSize((int) (initialPixelsPerWorldUnit * initialWindowWidth), (int) (initialPixelsPerWorldUnit * initialWindowHeight));
        jFrame.setVisible(true);

        this.windowCenter.x = xCenter;
        this.windowCenter.y = yCenter;

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



