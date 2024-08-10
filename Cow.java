// TODO TODO TODO keyHeld (probably) needs the same treatment as mouseHeld
// TODO: sound
// TODO: helper functions (snippets) go in Cow :) (all homeworks can extend Cow)
// TODO: a crash should cause the window to close
// TODO: a nice kitchen sink with COW branding, maybe some 3d stuff
// TODO: turn off automatic repaint (right now only happens when you resize the window)
// TODO: protect the entire student-facing API with checks for BEGINFRAME() already called
// TODO: set_canvas_clear_color

// // TODO: demos
// TODO: paint
// TODO: tic tac toe
// TODO: flappy bird

// // NOTE: limitations
// NOTE: drops very fast press and release

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;
import java.io.*;


class Cow {
    public static void main(String[] arguments) {
        // DemoKitchenSink.main(null);
        DemoTicTacToe.main(null);
    }

    /////////////////////////////////////////////////////////////////////////

    // TODO mouse and key stuff
    // TODO imgui

    static int MIN(int a, int b) { return Math.min(a, b); }
    static float MIN(double a, double b) { return (float) Math.min(a, b); }
    static int MAX(int a, int b) { return Math.max(a, b); }
    static float MAX(double a, double b) { return (float) Math.max(a, b); }
    static int ABS(int a) { return Math.abs(a); }
    static float ABS(double a) { return (float) Math.abs(a); }

    static Color RED    = Color.RED;
    static Color ORANGE = Color.ORANGE;
    static Color YELLOW = Color.YELLOW;
    static Color GREEN  = Color.GREEN;
    static Color BLUE   = Color.BLUE;
    static Color PINK   = Color.PINK;
    static Color CYAN   = Color.CYAN;
    static Color PURPLE = new Color(170 / 255.0f,   1 / 255.0f, 255 / 255.0f);
    static Color BROWN  = new Color(160 / 255.0f,  82 / 255.0f,  45 / 255.0f);
    static Color WHITE  = Color.WHITE;
    static Color GRAY   = Color.GRAY;
    static Color BLACK  = Color.BLACK;

    static final char LEFT_ARROW_KEY = KeyEvent.VK_LEFT;
    static final char RIGHT_ARROW_KEY = KeyEvent.VK_RIGHT;
    static final char BACKSPACE = KeyEvent.VK_BACK_SPACE;
    static final char SHIFT = KeyEvent.VK_SHIFT;
    static final char ENTER = KeyEvent.VK_ENTER;
    static final char CONTROL = KeyEvent.VK_CONTROL;
    static final char TAB = KeyEvent.VK_TAB;

    static boolean mousePressed;
    static boolean mouseHeld;
    static boolean mouseReleased;
    static float mouseX;
    static float mouseY;

    static boolean keyPressed (int i) { return _keyPressed[i];  }
    static boolean keyHeld    (int i) { return _keyHeld[i];     }
    static boolean keyReleased(int i) { return _keyReleased[i]; }
    static boolean keyToggled (int i) { return _keyToggled[i];  }

    static boolean _keyPressed[]  = new boolean[256];
    static boolean _keyHeld[]     = new boolean[256];
    static boolean _keyReleased[] = new boolean[256];
    static boolean _keyToggled[]  = new boolean[256];

    /////////////////////////////////////////////////////////////////////////

    // // TODO: User-Facing API 
    // takes doubles and return floats (so students don't have to worry about narrowing conversions)
    // all arguments in World coordinates unless otherwise specified (and then only in advanced API)
    // - hide advanced API with Java-style default args -- foo(a) { foo(a, defaults ...); }

    static float _canvas_left_World   = 0;
    static float _canvas_right_World  = 256;
    static float _canvas_bottom_World = 0;
    static float _canvas_top_World    = 256;
    static int _canvas_height_Pixel = 512;

    static float _canvas_get_aspect_ratio() { return _canvas_get_width_World() / _canvas_get_height_World(); }
    static int _canvas_get_width_Pixel() { return (int) (_canvas_get_aspect_ratio() * _canvas_height_Pixel); }
    static float _canvas_get_width_World() { return _canvas_right_World - _canvas_left_World; }
    static float _canvas_get_height_World() { return _canvas_top_World - _canvas_bottom_World; }
    static float _canvas_get_Pixel_per_World_ratio() { return _canvas_height_Pixel / _canvas_get_height_World(); }

    static int _xPIXELfromWORLD(double x_World) { return (int) (_canvas_get_Pixel_per_World_ratio() * (x_World - _canvas_left_World)); }
    static int _yPIXELfromWORLD(double y_World) { return (int) (_canvas_height_Pixel - (_canvas_get_Pixel_per_World_ratio() * (y_World - _canvas_bottom_World))); }
    static int _LPIXELfromWORLD(double length_world) { return (int) (_canvas_get_Pixel_per_World_ratio() * length_world); }
    static float _canvas_get_x_World_from_x_Pixel(int x_Pixel) { return (float) ((x_Pixel / _canvas_get_Pixel_per_World_ratio()) + _canvas_left_World); }
    static float _canvas_get_y_World_from_y_Pixel(int y_Pixel) { return (float) (((_canvas_height_Pixel - y_Pixel) / _canvas_get_Pixel_per_World_ratio()) + _canvas_bottom_World); }

    static int CANVAS_INIT_DEFAULT_MAX_DIMENSION_IN_PIXELS = 512;
    static void canvasConfig(double left, double bottom, double right, double top) { canvasConfig(left, bottom, right, top, CANVAS_INIT_DEFAULT_MAX_DIMENSION_IN_PIXELS); }
    static void canvasConfig(double left, double bottom, double right, double top, int maxDimensionInPixels) {
        _canvas_left_World   = (float) left;
        _canvas_right_World  = (float) right;
        _canvas_bottom_World = (float) bottom;
        _canvas_top_World    = (float) top;

        float max_dim_World = Math.max(_canvas_get_width_World(), _canvas_get_height_World());
        float Pixel_per_World = maxDimensionInPixels / max_dim_World;
        _canvas_height_Pixel = (int) (Pixel_per_World * _canvas_get_height_World());

        if (_cow_initialized) _canvasReattach();
    }
    static void _canvasReattach() {
        _buffered_image = new BufferedImage(_canvas_get_width_Pixel(), _canvas_height_Pixel, BufferedImage.TYPE_INT_ARGB);
        _buffered_image_graphics = _buffered_image.createGraphics();
        _jPanel_extender.setPreferredSize(new Dimension(_canvas_get_width_Pixel(), _canvas_height_Pixel));
        _jFrame.pack();
    }

    static Color DRAW_LINE_DEFAULT_COLOR = BLACK;
    static double DRAW_LINE_DEFAULT_THICKNESS = 2.0;
    static void drawLine(double x1, double y1, double x2, double y2) { drawLine(x1, y1, x2, y2, DRAW_LINE_DEFAULT_COLOR, DRAW_LINE_DEFAULT_THICKNESS); }
    static void drawLine(double x1, double y1, double x2, double y2, Color color) { drawLine(x1, y1, x2, y2, color, DRAW_LINE_DEFAULT_THICKNESS); }
    static void drawLine(double x1, double y1, double x2, double y2, Color color, double thickness) {
        _draw_set_color(color);
        _draw_set_line_thickness(thickness);
        _buffered_image_graphics.drawLine(_xPIXELfromWORLD(x1), _yPIXELfromWORLD(y1), _xPIXELfromWORLD(x2), _yPIXELfromWORLD(y2));
    }

    static void drawRectangle(double x1, double y1, double x2, double y2, Color color) {
        _draw_set_color(color);
        _draw_rectangle(x1, y1, x2, y2, false);
    }

    // // TODO: Utility API

    static void _set_monospaced_font_character_width(double character_width) { // setFont with World character_width
        int char_height_Pixel = 4096;
        _buffered_image_graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, char_height_Pixel)); 
        FontMetrics fontMetrics = _buffered_image_graphics.getFontMetrics(); 
        int char_width_Pixel = fontMetrics.charWidth('A');
        double char_height_World = char_height_Pixel * (character_width / char_width_Pixel);
        char_height_Pixel = _LPIXELfromWORLD(char_height_World);
        _buffered_image_graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, char_height_Pixel)); 
    }

    // // core drawing API
    // _draw_set_color(r, g, b)
    // _draw_set_color(r, g, b, a)
    // _draw_set_color(color)
    // _draw_set_color(color, a)
    // _draw_set_line_thickness(diameter|line_width)
    // draw_set_polygon_mode(FILL|OUTLINE) // NOTE: lines are always drawn
    // draw_begin(CIRCLES|RECTANGLES|LINES|LINE_STRIP|TRIANGLES|QUADS)
    // draw_vertex(double x, double y)
    // draw_end()

    static void _draw_set_line_thickness(double w) {
        assert w >= 0;
        ((Graphics2D) _buffered_image_graphics).setStroke(new BasicStroke((float) w));
    }

    static void _draw_set_color(Color color, double a) {
        assert a >= 0;
        assert a <= 1;
        _buffered_image_graphics.setColor(new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float) a));
    }

    static void _draw_set_color(Color color) {
        _buffered_image_graphics.setColor(color);
    }

    static void _draw_set_color(double r, double g, double b, double a) {
        assert r >= 0;
        assert r <= 1;
        assert g >= 0;
        assert g <= 1;
        assert b >= 0;
        assert b <= 1;
        assert a >= 0;
        assert a <= 1;
        _draw_set_color(new Color((float) r, (float) g, (float) b, (float) a));
    }

    static void _draw_set_color(double r, double g, double b) {
        _draw_set_color(r, g, b, 1.0);
    }

    static Color color_rainbow_swirl(double time) {
        double TAU   = 6.283;
        double red   = 0.5f + 0.5f * (float) Math.cos(TAU * ( 0.000 - time));
        double green = 0.5f + 0.5f * (float) Math.cos(TAU * ( 0.333 - time));
        double blue  = 0.5f + 0.5f * (float) Math.cos(TAU * (-0.333 - time));
        return new Color((float) red, (float) green, (float) blue);
    }



    static void fill_rectangle(double x1, double y1, double x2, double y2) { _draw_rectangle(x1, y1, x2, y2, false); }
    static void outline_rectangle(double x1, double y1, double x2, double y2) { _draw_rectangle(x1, y1, x2, y2, true); }
    static void _draw_rectangle(double x1, double y1, double x2, double y2, boolean outlined) {
        int Xx1 = _xPIXELfromWORLD(x1);
        int Yy1 = _yPIXELfromWORLD(y1);
        int Xx2 = _xPIXELfromWORLD(x2);
        int Yy2 = _yPIXELfromWORLD(y2);
        int arg1 = Math.min(Xx1, Xx2);
        int arg2 = Math.min(Yy1, Yy2);
        int arg3 = Math.abs(Xx1 - Xx2);
        int arg4 = Math.abs(Yy1 - Yy2);
        if (!outlined) {
            _buffered_image_graphics.fillRect(arg1, arg2, arg3, arg4);
        } else {
            _buffered_image_graphics.drawRect(arg1, arg2, arg3, arg4);
        }
    }

    // static void fill_center_rectangle(double x, double y, double width, double height) { _draw_center_rectangle(x, y, width, height, false); }
    // static void outline_center_rectangle(double x, double y, double width, double height) { _draw_center_rectangle(x, y, width, height, true); }
    // static void _draw_center_rectangle(double x, double y, double width, double height, boolean outlined) {
    //     assert width >= 0;
    //     assert height >= 0;
    //     double half_width = width / 2;
    //     double half_height = height / 2;
    //     _draw_corner_rectangle(x - half_width, y - half_height, x + half_width, y + half_height, outlined);
    // }

    static void fill_circle(double x, double y, double r) { _draw_circle(x, y, r, false); }
    static void outline_circle(double x, double y, double r) { _draw_circle(x, y, r, true); }
    static void _draw_circle(double x, double y, double r, boolean outlined) {
        int arg1 = _xPIXELfromWORLD(x - r);
        int arg2 = _yPIXELfromWORLD(y + r);
        int arg3 = _LPIXELfromWORLD(2 * r);
        int arg4 = arg3;
        if (!outlined) {
            _buffered_image_graphics.fillOval(arg1, arg2, arg3, arg4);
        } else {
            _buffered_image_graphics.drawOval(arg1, arg2, arg3, arg4);
        }
    }

    // TODO: camera (ability to zoom out, pan)
    // TODO: ability to resize window
    // TODO: window decorations


    static BufferedImage _buffered_image;
    static Graphics _buffered_image_graphics;
    static CowJPanelExtender _jPanel_extender;
    static JFrame _jFrame;
    static boolean _cow_initialized;

    static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {}
    }

    static void _cow_safe_attempt_initialize() {
        if (!_cow_initialized) {
            _cow_initialized = true;

            _buffered_image = new BufferedImage(_canvas_get_width_Pixel(), _canvas_height_Pixel, BufferedImage.TYPE_INT_ARGB);
            assert _buffered_image != null;
            _buffered_image_graphics = _buffered_image.createGraphics();
            assert _buffered_image_graphics != null;

            { // Trigger and suppress one-time Mac warnings about missing fonts.
                PrintStream systemDotErr = System.err;
                System.setErr(new PrintStream(new NullOutputStream()));
                _buffered_image_graphics.getFontMetrics(); 
                System.setErr(systemDotErr);
            }

            _jPanel_extender = new CowJPanelExtender();
            _jPanel_extender.setPreferredSize(new Dimension(_canvas_get_width_Pixel(), _canvas_height_Pixel));

            _jFrame = new JFrame("cs136 2024");
            _jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _jFrame.setLocation(0, 0);
            _jFrame.getContentPane().add(_jPanel_extender, BorderLayout.CENTER);
            _jFrame.pack();
            _jFrame.setVisible(true);
        }
    }


    static boolean beginFrame() {
        _cow_safe_attempt_initialize();

        { // *_pressed, *_released
            { // mousePressed, mouseReleased
                mousePressed = _jPanel_extender._mousePressed;
                _jPanel_extender._mousePressed = false;
                if (mousePressed) mouseHeld = true;

                mouseReleased = _jPanel_extender._mouseReleased;
                _jPanel_extender._mouseReleased = false;
                if (mouseReleased) mouseHeld = false;
            }
            { // keyboard
                for (int i = 0; i < 256; ++i) {
                    _keyPressed[i]  = _jPanel_extender._keyPressed[i];
                    _keyReleased[i] = _jPanel_extender._keyReleased[i];
                    _jPanel_extender._keyPressed[i] = false;
                    _jPanel_extender._keyReleased[i] = false;
                    if (_keyPressed[i]) {
                        _keyHeld[i] = true;
                        _keyToggled[i] = !_keyToggled[i];
                    }
                    if (_keyReleased[i]) _keyHeld[i] = false;
                }
            }
        }

        _jPanel_extender.repaint();

        try { Thread.sleep(1000 / 60); } catch (Exception exception) {};

        // beginning of next frame

        { // mouse
            Point point;
            {
                point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, _jPanel_extender);
            }
            mouseX = _canvas_get_x_World_from_x_Pixel(point.x);
            mouseY = _canvas_get_y_World_from_y_Pixel(point.y);
        }


        { // canvas
            Color tmp = _buffered_image_graphics.getColor();
            _buffered_image_graphics.setColor(Color.white);
            _buffered_image_graphics.fillRect(0, 0, _canvas_get_width_Pixel(), _canvas_height_Pixel);
            _buffered_image_graphics.setColor(tmp);
        }

        // return !(_keyHeld[CONTROL] && _keyPressed['Q']);
        return true;
    }
}


class CowJPanelExtender extends JPanel {
    private static final long serialVersionUID = 1L;

    boolean _mousePressed;
    boolean _mouseReleased;
    boolean _keyPressed[]  = new boolean[256];
    boolean _keyReleased[] = new boolean[256];

    CowJPanelExtender() {
        super();

        this.addMouseListener( 
                new MouseAdapter() {
                    @Override public void mousePressed(MouseEvent e) { _mousePressed = true; }
                    @Override public void mouseReleased(MouseEvent e) { _mouseReleased = true; }
                    }
                );

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            synchronized (Cow.class) {
                int _key = event.getKeyCode();
                if (_key >= 256) return false;
                char key = (char) _key;

                if (event.getID() == KeyEvent.KEY_PRESSED) {
                    _keyPressed[key] = true;
                } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                    _keyReleased[key] = true;
                }

                return false;
            }
        });


    }

    @Override
    public void paintComponent(Graphics paintComponentGraphics) { 
        super.paintComponent(paintComponentGraphics);
        while (Cow._buffered_image_graphics == null) {}
        while (Cow._buffered_image == null) {}
        paintComponentGraphics.drawImage(Cow._buffered_image, 0, 0, null);
    }
}






class DemoTicTacToe extends Cow {
    final static int PLAYER_NONE = 0;
    final static int PLAYER_X    = 1;
    final static int PLAYER_O    = 2;

    // state
    static int board[][] = new int[3][3]; // board[row (y)][column (x)]
    static int current_player = PLAYER_X;
    static int winner = PLAYER_NONE;
    static int turn = 0;
    static double win_line_x1;
    static double win_line_x2;
    static double win_line_y1;
    static double win_line_y2;

    static boolean game_is_over() {
        return (winner != PLAYER_NONE) || (turn == 9);
    }

    public static void main(String[] arguments) {

        // configure
        canvasConfig(0, 0, 3, 3);

        // loop
        while (beginFrame()) {
            int hot_row = (int) Math.floor(mouseY);
            int hot_column = (int) Math.floor(mouseX);

            if (!game_is_over()) { // update
                if (mousePressed && (board[hot_row][hot_column] == PLAYER_NONE)) { // make move
                    ++turn;
                    board[hot_row][hot_column] = current_player;
                    current_player = (current_player == PLAYER_X) ? PLAYER_O : PLAYER_X;
                }

                { // check for game over

                    // rows
                    for (int row = 0; row < 3; ++row) {
                        if ((board[row][0] != PLAYER_NONE) && ((board[row][0] == board[row][1]) && (board[row][0] == board[row][2]))) {
                            winner = board[row][0];
                            win_line_x1 = 0.0;
                            win_line_x2 = 3.0;
                            win_line_y1 = row + 0.5;
                            win_line_y2 = win_line_y1;
                        }
                    }

                    // columns
                    for (int column = 0; column < 3; ++column) {
                        if ((board[0][column] != PLAYER_NONE) && ((board[0][column] == board[1][column]) && (board[0][column] == board[2][column]))) {
                            winner = board[0][column];
                            win_line_x1 = column + 0.5;
                            win_line_x2 = win_line_x1;
                            win_line_y1 = 0.0;
                            win_line_y2 = 3.0;
                        }
                    }

                    // diagonals
                    if ((board[0][0] != PLAYER_NONE) && ((board[0][0] == board[1][1]) && (board[0][0] == board[2][2]))) {
                        winner = board[0][0];
                        win_line_x1 = 0.0;
                        win_line_y1 = 0.0;
                        win_line_x2 = 3.0;
                        win_line_y2 = 3.0;
                    }
                    if ((board[0][2] != PLAYER_NONE) && ((board[0][2] == board[1][1]) && (board[0][2] == board[2][0]))) {
                        winner = board[0][2];
                        win_line_x1 = 0.0;
                        win_line_y1 = 3.0;
                        win_line_x2 = 3.0;
                        win_line_y2 = 0.0;
                    }

                }
            }

            { // draw
                if (!game_is_over()) { // hot square
                    _draw_set_color(Color.yellow, 0.5);
                    fill_rectangle(hot_column, hot_row, hot_column + 1, hot_row + 1);
                }

                { // board lines
                    _draw_set_line_thickness(2.0);
                    drawLine(1, 0, 1, 3, BLACK);
                    drawLine(2, 0, 2, 3, BLACK);
                    drawLine(0, 1, 3, 1, BLACK);
                    drawLine(0, 2, 3, 2, BLACK);
                }

                { // X's and O's
                    double epsilon = 0.1;
                    for (int row = 0; row < 3; ++row) {
                        for (int column = 0; column < 3; ++column) {
                            if (board[row][column] == PLAYER_X) {
                                drawLine(column + epsilon, row + epsilon, column + 1 - epsilon, row + 1 - epsilon, BLACK);
                                drawLine(column + epsilon, row + 1 - epsilon, column + 1 - epsilon, row + epsilon, BLACK);
                            } else if (board[row][column] == PLAYER_O) {
                                outline_circle(column + 0.5, row + 0.5, 0.5 - epsilon);
                            }
                        }
                    }
                }

                if (winner != PLAYER_NONE) { // winning line
                    drawLine(win_line_x1, win_line_y1, win_line_x2, win_line_y2, BLUE, 6.0);
                }
            }
        }
    }
}
