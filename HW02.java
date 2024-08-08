import java.util.*;
import java.awt.event.KeyEvent;
class HW02 extends Cow {
    public static void main(String[] arguments) {

        // TODO: make font size of draw_string in world coordinates
        // TODO: Key.LeftArrowKey RightArrowKey Backspace
        // TODO: drawLine
        // TODO: drawString(x, y, size, Color.RED)
        //
        // TODO
        final double CHARACTER_WIDTH = 1;

        {
            set_canvas_world_size(256.0, 32.0); // TODO: dynamic resizing of app?
            canvas_pixel_height = 64;
            // FORNOW
            // TODO: give students a reasonable error message if they try to drawCircle outside of the while(begin_frame()) { ... }
            _cow_safe_attempt_initialize();
            draw_set_color(1.0, 0.0, 0.0);
        }


        char[] buffer = new char[16];
        int i = 0;
        while (begin_frame()) {

            // // NOTES
            // NOTE: You are NOT allowed to use the String class.
            //       You must work directly with the char[] buffer.
            //       You will use some for loops.
            // // A-
            // TODO: A-Z
            // TODO: 0-9
            // TODO: a-z
            // TODO: a-z
            // TODO: display cursor bar
            // TODO: Space
            // TODO: Backspace (must do what Jim would expect)
            // TODO: LeftArrowKey, RightArrowKey
            // NOTE: can't have any out of bounds errors
            // // A
            // TODO: make the cursor bar blink
            // TODO: mouse input click to move cursor
            // // A+
            // TODO: mouse input click and drag to highlight (typing if highlighted does what Jim would expect)
            // TODO: mouse input double-click to select all characters
            // // A++
            // TODO: save to .txt
            for (char c = 'A'; c <= 'Z'; ++c) {
                if (keyPressed[c]) buffer[i++] = c;
            }
            if (keyPressed[' ']) buffer[i++] = ' ';
            if (keyPressed[KeyEvent.VK_LEFT]) {
                if (i > 0) --i;
            }

            { // draw (scary)
                int length = -1;
                while (buffer[++length] != 0);
                String string = new String(buffer, 0, length);
                draw_string(string, 0.0, 8.0, 48, false); // TODO?: font size in world coordinates
                double x = i * 14.0;
                draw_line(x, 8.0, x, 26.0);
            }
        }

    }
}
