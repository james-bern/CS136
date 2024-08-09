import java.util.*;
import java.awt.*;

class HW02 extends Cow {
    public static void main(String[] arguments) {

        // TODO: make font size of draw_string in world coordinates
        // TODO: Key.LeftArrowKey RightArrowKey Backspace
        // TODO: drawLine
        // TODO: drawString(x, y, size, Color.RED)
        //
        // TODO

        final double CHARACTER_WIDTH = 1.0;
        final char[] buffer = new char[16];
        canvasConfig(0.0, -CHARACTER_WIDTH, buffer.length, 2 * CHARACTER_WIDTH, 512);

        int cursor = 0;
        int length = 0;

        while (beginFrame()) {

            {
                // // TODO (Jim/Nate)
                // drawString should take the bottom left corner
                // record animated gif as example of perfect program

                // // README
                // notion of compression-orientation (pseudocode)
                // diagram of problem's geometry
                // explanation of buffer and null-termination
                // keyPressed[...]
                // keyHeld[...]
                // mouseX
                // mouseY
                // LEFT_ARROW
                // RIGHT_ARROW
                // BACKSPACE
                // SHIFT
                // drawLine
                // for (char c = 'A'; c <= 'Z'; ++c) { ... }
                // ... = (char)('a' + (c - 'A'));
                // A+ hint on selection_cursor
                // 
                // // NOTES
                // NOTE: You are NOT allowed to use the String class.
                //       You must work directly with the char[] buffer.
                //       You will use some for loops.
                // NOTE: You are NOT allowed to create any new arrays.
                // // A-
                // TODO: A-Z
                // TODO: 0-9
                // TODO: a-z
                // TODO: display cursor bar
                // TODO: Space
                // TODO: Backspace (must do what Jim would expect)
                // NOTE: can't have any out of bounds errors
                // // A
                // TODO: make the cursor bar blink
                // TODO: mouse input click to move cursor
                // // A+
                // TODO: mouse input click and drag to highlight (typing if highlighted does what Jim would expect)
                // TODO: mouse input double-click to select all characters
                // // A++
                // TODO: save to .txt
            }
            for (char c = 'A'; c <= 'Z'; ++c) { // README
                if (keyPressed[c]) {
                    if (!keyHeld[SHIFT]) {
                        buffer[length++] = (char)('a' + (c - 'A'));
                    } else {
                        buffer[length++] = c;
                    }
                }
            }
            if (keyPressed[' ']) buffer[length++] = ' ';
            if (keyPressed[BACKSPACE]) length--;

            { // draw
                drawLine(cursor, -0.5, cursor, 1.5, BLACK, 3.0);
                _set_monospaced_font_character_width(CHARACTER_WIDTH);
                _buffered_image_graphics.drawChars(buffer, 0, length, _xPIXELfromWORLD(0.0), _yPIXELfromWORLD(0.0));
            }
        }

    }
}
