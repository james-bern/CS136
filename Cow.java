// TODO: sound
// TODO: helper functions (snippets) go in Cow :) (all homeworks can extend Cow)
// TODO: a crash should cause the window to close
// TODO: a nice kitchen sink with COW branding, maybe some 3d stuff
// TODO: turn off automatic repaint (right now only happens when you resize the window)
// TODO: protect the entire student-facing API with checks for begin_frame() already called
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


    static boolean mouse_held;
    static boolean _mouse_held_prev;
    static boolean mouse_pressed;
    static boolean mouse_released;
    static float mouse_x;
    static float mouse_y;

    static boolean _key_held_prev[]     = new boolean[256];
    static boolean key_held[]     = new boolean[256];
    static boolean key_released[] = new boolean[256];
    static boolean keyPressed[]  = new boolean[256];
    static boolean key_toggled[]  = new boolean[256];

    /////////////////////////////////////////////////////////////////////////

    // TODO: user-facing API takes doubles and returns floats

    static int canvas_pixel_height = 512;
    static float canvas_world_left   = 0;
    static float canvas_world_bottom = 0;
    static float canvas_world_width  = 256;
    static float canvas_world_height = 256;
    static float canvas_aspect_ratio() { return canvas_world_width / canvas_world_height; }
    static int canvas_pixel_width() {
        return (int) (canvas_aspect_ratio() * canvas_pixel_height);
    }

    static void set_canvas_world_left_bottom_corner(double x, double y) {
        canvas_world_left = (float) x;
        canvas_world_bottom = (float) y;
    }

    static void set_canvas_world_size(double x, double y) {
        canvas_world_width = (float) x;
        canvas_world_height = (float) y;
    }



    // TODO: consider this API
    static void begin_line_strip() {

    }

    static void end_line_strip() {

    }

    static void draw_string(String string, double _x, double _y, int fontSize, boolean center) {
        {
            int x = X_pixel_from_world(_x);
            int y = Y_pixel_from_world(_y);
            if (center) {
                FontMetrics fontMetrics = _buffered_image_graphics.getFontMetrics(); 
                x -= 0.5 * fontMetrics.stringWidth(string);
                y += 0.25 * fontMetrics.getHeight();
            }
            _buffered_image_graphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize)); 
            _buffered_image_graphics.drawString(string, x, y);
        }
    }

    // // core drawing API
    // draw_set_color(r, g, b)
    // draw_set_color(r, g, b, a)
    // draw_set_color(color)
    // draw_set_color(color, a)
    // draw_set_line_thickness(diameter|line_width)
    // draw_set_polygon_mode(FILL|OUTLINE) // NOTE: lines are always drawn
    // draw_begin(CIRCLES|RECTANGLES|LINES|LINE_STRIP|TRIANGLES|QUADS)
    // draw_vertex(double x, double y)
    // draw_end()

    static void draw_set_line_thickness(double w) {
        assert w >= 0;
        ((Graphics2D) _buffered_image_graphics).setStroke(new BasicStroke((float) w));
    }

    static void draw_set_color(Color color, double a) {
        assert a >= 0;
        assert a <= 1;
        _buffered_image_graphics.setColor(new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (float) a));
    }

    static void draw_set_color(Color color) {
        _buffered_image_graphics.setColor(color);
    }

    static void draw_set_color(double r, double g, double b, double a) {
        assert r >= 0;
        assert r <= 1;
        assert g >= 0;
        assert g <= 1;
        assert b >= 0;
        assert b <= 1;
        assert a >= 0;
        assert a <= 1;
        draw_set_color(new Color((float) r, (float) g, (float) b, (float) a));
    }

    static void draw_set_color(double r, double g, double b) {
        draw_set_color(r, g, b, 1.0);
    }

    static Color color_rainbow_swirl(double time) {
        double TAU   = 6.283;
        double red   = 0.5f + 0.5f * (float) Math.cos(TAU * ( 0.000 - time));
        double green = 0.5f + 0.5f * (float) Math.cos(TAU * ( 0.333 - time));
        double blue  = 0.5f + 0.5f * (float) Math.cos(TAU * (-0.333 - time));
        return new Color((float) red, (float) green, (float) blue);
    }


    static void draw_line(double x1, double y1, double x2, double y2) {
        _buffered_image_graphics.drawLine(X_pixel_from_world(x1), Y_pixel_from_world(y1), X_pixel_from_world(x2), Y_pixel_from_world(y2));
    }

    static void fill_rectangle(double x1, double y1, double x2, double y2) { _draw_rectangle(x1, y1, x2, y2, false); }
    static void outline_rectangle(double x1, double y1, double x2, double y2) { _draw_rectangle(x1, y1, x2, y2, true); }
    static void _draw_rectangle(double x1, double y1, double x2, double y2, boolean outlined) {
        int Xx1 = X_pixel_from_world(x1);
        int Yy1 = Y_pixel_from_world(y1);
        int Xx2 = X_pixel_from_world(x2);
        int Yy2 = Y_pixel_from_world(y2);
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
        int arg1 = X_pixel_from_world(x - r);
        int arg2 = Y_pixel_from_world(y + r);
        int arg3 = L_pixel_from_world(2 * r);
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

    static float pixel_from_world_scale() {
        return canvas_pixel_height / canvas_world_height;
    }

    static int X_pixel_from_world(double x_world) {
        return (int) (pixel_from_world_scale() * (x_world - canvas_world_left));
    }

    static int Y_pixel_from_world(double y_world) {
        return (int) (canvas_pixel_height - (pixel_from_world_scale() * (y_world - canvas_world_bottom)));
    }

    static int L_pixel_from_world(double length_world) {
        return (int) (pixel_from_world_scale() * length_world);
    }

    static float X_world_from_pixel(int x_pixel) {
        return (float) ((x_pixel / pixel_from_world_scale()) + canvas_world_left);
    }

    static float Y_world_from_pixel(int y_pixel) {
        return (float) (((canvas_pixel_height - y_pixel) / pixel_from_world_scale()) + canvas_world_bottom);
    }

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

            _buffered_image = new BufferedImage(canvas_pixel_width(), canvas_pixel_height, BufferedImage.TYPE_INT_ARGB);
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
            _jPanel_extender.setPreferredSize(new Dimension(canvas_pixel_width(), canvas_pixel_height));

            _jFrame = new JFrame("cs136 2024");
            _jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            _jFrame.setLocation(0, 0);
            _jFrame.getContentPane().add(_jPanel_extender, BorderLayout.CENTER);
            _jFrame.pack();
            _jFrame.setVisible(true);
        }
    }


    static boolean begin_frame() {
        _cow_safe_attempt_initialize();

        { // *_pressed, *_released
            { // mouse_pressed, mouse_released
                mouse_pressed =  (!_mouse_held_prev && mouse_held);
                mouse_released = (_mouse_held_prev && !mouse_held);
                _mouse_held_prev = mouse_held;
            }
            { // keyboard
                for (int i = 0; i < 256; ++i) {
                    keyPressed[i]  = (!_key_held_prev[i] && key_held[i]);
                    key_released[i] = (_key_held_prev[i] && !key_held[i]);
                    if (key_released[i]) key_toggled[i] = !key_toggled[i];
                }
                System.arraycopy(key_held, 0, _key_held_prev, 0, key_held.length); // NOTE: silly order of arguments
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
            mouse_x = X_world_from_pixel(point.x);
            mouse_y = Y_world_from_pixel(point.y);
        }


        { // canvas
            Color tmp = _buffered_image_graphics.getColor();
            _buffered_image_graphics.setColor(Color.white);
            _buffered_image_graphics.fillRect(0, 0, canvas_pixel_width(), canvas_pixel_height);
            _buffered_image_graphics.setColor(tmp);
        }


        return true;
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
        set_canvas_world_left_bottom_corner(0, 0);
        set_canvas_world_size(3, 3);
        canvas_pixel_height = 256;

        System.out.println("Press the K key to crash.");

        // loop
        while (begin_frame()) {
            int hot_row = (int) Math.floor(mouse_y);
            int hot_column = (int) Math.floor(mouse_x);

            if (keyPressed['K']) {
                int i = 1 / 0;
            }

            if (!game_is_over()) { // update
                if (mouse_pressed && (board[hot_row][hot_column] == PLAYER_NONE)) { // make move
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
                    draw_set_color(Color.yellow, 0.5);
                    fill_rectangle(hot_column, hot_row, hot_column + 1, hot_row + 1);
                }

                { // board lines
                    draw_set_color(Color.black);
                    draw_set_line_thickness(2.0);
                    draw_line(1, 0, 1, 3);
                    draw_line(2, 0, 2, 3);
                    draw_line(0, 1, 3, 1);
                    draw_line(0, 2, 3, 2);
                }

                { // X's and O's
                    draw_set_color(Color.black);
                    draw_set_line_thickness(3.0);
                    double epsilon = 0.1;
                    for (int row = 0; row < 3; ++row) {
                        for (int column = 0; column < 3; ++column) {
                            if (board[row][column] == PLAYER_X) {
                                draw_line(column + epsilon, row + epsilon, column + 1 - epsilon, row + 1 - epsilon);
                                draw_line(column + epsilon, row + 1 - epsilon, column + 1 - epsilon, row + epsilon);
                            } else if (board[row][column] == PLAYER_O) {
                                outline_circle(column + 0.5, row + 0.5, 0.5 - epsilon);
                            }
                        }
                    }
                }

                if (winner != PLAYER_NONE) { // winning line
                    draw_set_color(Color.magenta, 0.5);
                    draw_set_line_thickness(6.0);
                    draw_line(win_line_x1, win_line_y1, win_line_x2, win_line_y2);
                }
            }
        }
    }
}

class DemoKitchenSink extends Cow {
    static class Particle {
        double x;
        double y;
        Color color;
    };

    public static void main(String[] arguments) {
        // configure
        set_canvas_world_left_bottom_corner(-5, -5);
        set_canvas_world_size(10, 10);

        // state
        ArrayList<Particle> particles = new ArrayList<>();
        double x = 0.0;
        double y = 0.0;

        // loop
        while (begin_frame()) {
            if (!key_toggled['P']) { // update

                if (mouse_pressed) {
                    Particle particle = new Particle();
                    particle.x = mouse_x;
                    particle.y = mouse_y;
                    particle.color = Color.orange;
                    particles.add(particle);
                }

                if (mouse_released) {
                    Particle particle = new Particle();
                    particle.x = mouse_x;
                    particle.y = mouse_y;
                    particle.color = Color.cyan;
                    particles.add(particle);
                }

                {
                    double delta = 0.1;
                    if (key_held['W']) y += delta;
                    if (key_held['A']) x -= delta;
                    if (key_held['S']) y -= delta;
                    if (key_held['D']) x += delta;
                }
            }

            { // draw

                /*
                   outline_rectangle(-5, -5, 5, 5, mouse_held ? GREEN : RED, 8.0);
                   draw_line(mouse_x, mouse_y, 3, 3, MAGENTA, 4.0);
                   fill_circle(x, y, 1, color_rainbow_swirl(time));
                   outline_circle(x, y, 1, BLACK, 3.0);
                   for (int i = 0; i < particles.size(); ++i) {
                   fill_circle(particles.get(i).x, particles.get(i).y, 0.1, particles.get(i).color);
                   }
                   */

                //////////////////////////////////

                /*
                   outline_rectangle(-5, -5, 5, 5, mouse_held ? Color.green : Color.red, 8.0);
                   draw_line(mouse_x, mouse_y, 3, 3, Color.magenta, 4.0);
                   fill_circle(x, y, 1, color_rainbow_swirl(time));
                   outline_circle(x, y, 1, Color.black, 3.0);
                   for (int i = 0; i < particles.size(); ++i) {
                   fill_circle(particles.get(i).x, particles.get(i).y, 0.1, particles.get(i).color);
                   }
                   */

                //////////////////////////////////

                /*
                   draw_set_color(mouse_held ? Color.green : Color.red);
                   draw_set_line_thickness(8.0);
                   outline_rectangle(-5, -5, 5, 5);

                   draw_set_color(Color.magenta);
                   draw_set_line_thickness(4.0);
                   draw_line(mouse_x, mouse_y, 3, 3);

                   draw_set_color(color_rainbow_swirl(time), 0.5);
                   fill_circle(x, y, 1);
                   draw_set_color(0.0, 0.0, 0.0);
                   draw_set_line_thickness(3.0);
                   outline_circle(x, y, 1);

                   for (int i = 0; i < particles.size(); ++i) {
                   draw_set_color(particles.get(i).color);
                   fill_circle(particles.get(i).x, particles.get(i).y, 0.1);
                   }
                   */

                //////////////////////////////////

                /*
                   draw_begin(RECTANGLES);
                   draw_set_polygon_mode(OUTLINE);
                   draw_set_color(mouse_held ? Color.green : Color.red);
                   draw_set_line_thickness(8.0);
                   draw_vertex(-5, -5);
                   draw_vertex( 5,  5);
                   draw_end();

                   draw_begin(LINES);
                   draw_set_polygon_mode(FILL);
                   draw_set_color(Color.magenta);
                   draw_set_line_thickness(4.0);
                   draw_vertex(mouse_x, mouse_y);
                   draw_vertex(3, 3);
                   draw_end();

                   draw_begin(CIRCLES);
                   draw_set_polygon_mode(FILL);
                   draw_set_color(color_rainbow_swirl(time), 0.5);
                   draw_set_circle_diameter(2.0);
                   draw_vertex(x, y);
                   draw_set_polygon_mode(OUTLINE);
                   draw_set_color(Color.black);
                   draw_set_line_thickness(3.0);
                   draw_vertex(x, y);
                   draw_end();

                   draw_begin(CIRCLES);
                   draw_set_polygon_mode(FILL);
                   draw_set_circle_diameter(1.0);
                   for (int i = 0; i < particles.size(); ++i) {
                   draw_set_color(particles.get(i).color);
                   draw_vertex(particles.get(i).x, particles.get(i).y);
                   }
                   draw_end();
                   */

                ///////////////////////////////////

                draw_set_color(Color.black);
                draw_string("Hello", x, y, 24, true);
            }
        }
    }
}


class CowJPanelExtender extends JPanel {
    private static final long serialVersionUID = 1L;

    CowJPanelExtender() {
        super();

        this.addMouseListener( 
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Cow.mouse_held = true;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        Cow.mouse_held = false;
                    }
                }
                );

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            synchronized (Cow.class) {
                int _key = event.getKeyCode();
                if (_key >= 256) return false;
                char key = (char) _key;

                if (event.getID() == KeyEvent.KEY_PRESSED) {
                    Cow.key_held[key] = true;
                } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                    Cow.key_held[key] = false;
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

