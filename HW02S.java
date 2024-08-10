// // TODO (Jim/Nate)
// drawString should take the bottom left corner
// record animated gif as example of perfect program
// MIN, MAX, ABS

// // README
// jim and the TA's will stress-test your code
// notion of compression-orientation (pseudocode)
// diagram of problem's geometry
// explanation of buffer and null-termination
// keyPressed[...]
// keyHeld[...]
// mouseX
// mouseY
// LEFT_ARROW_KEY
// RIGHT_ARROW_KEY
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
// NOTE: You should have minimal repetition.
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
// TODO: mouse input click to move cursor (HINT: your code should contain a 0.5 or similar)
// TODO: if the user presses enter and the buffer contains "Passw0rd", replace the contents of the buffer with "accepted"
// // A+
// TODO: mouse input click and drag to highlight (typing if highlighted does what Jim would expect)
// TODO: hold control and press left / right arrow keys to ...
// TODO: mouse input double-click to select all characters
// // A++
// TODO: save to .txt

class HW02S extends Cow {
    public static void main(String[] arguments) {

        final char[] buffer = new char[8];
        int length = 0;
        int cursor = 0;
        int select = 0;
        final double CHARACTER_WIDTH = 1.0;
        canvasConfig(0.0, -CHARACTER_WIDTH, buffer.length, 2 * CHARACTER_WIDTH, 512);

        int blinkCounter = 0;

        while (beginFrame()) {
            boolean insertCharacterValid = (length != buffer.length);
            boolean backspaceValid = ((length != 0) && (cursor != 0));
            boolean leftArrowKeyValid = (cursor != 0);
            boolean rightArrowKeyValid = (cursor != length);

            if (insertCharacterValid) {
                char newCharacter; {
                    newCharacter = 0;
                    for (char c = 'A'; c <= 'Z'; ++c) {
                        if (keyPressed(c)) {
                            if (!keyHeld(SHIFT)) {
                                newCharacter = (char)('a' + (c - 'A'));
                            } else {
                                newCharacter = c;
                            }
                        }
                    }
                    for (char c = '0'; c <= '9'; ++c) {
                        if (keyPressed(c)) newCharacter = c;
                    }
                    if (keyPressed(' ')) newCharacter = ' ';
                }
                if (newCharacter != 0) {
                    if (cursor == select) {
                        for (int i = length; i > cursor; --i) buffer[i] = buffer[i - 1];
                        buffer[cursor] = newCharacter;
                        select = ++cursor;
                        ++length;
                    } else {
                        // TODO
                    }
                }
            }

            if (backspaceValid && keyPressed(BACKSPACE)) {
                if (cursor == select) {
                    for (int i = cursor; i < length; ++i) buffer[i - 1] = buffer[i];
                    select = --cursor;
                    --length;
                } else {
                    // TODO
                }
            }

            if (leftArrowKeyValid && keyPressed(LEFT_ARROW_KEY)) {
                if (select != cursor) {
                    cursor = Math.min(select, cursor);
                } else {
                    select = --cursor;
                }
            }

            if (rightArrowKeyValid && keyPressed(RIGHT_ARROW_KEY)) {
                if (select != cursor) {
                    cursor = Math.max(select, cursor);
                } else {
                    select = ++cursor;
                }
            }

            if (keyPressed(ENTER)) {
                if (true
                        && (buffer[0] == 'P')
                        && (buffer[1] == 'a')
                        && (buffer[2] == 's')
                        && (buffer[3] == 's')
                        && (buffer[4] == 'w')
                        && (buffer[5] == '0')
                        && (buffer[6] == 'r')
                        && (buffer[7] == 'd')) {
                    buffer[0] = 'a';
                    buffer[1] = 'c';
                    buffer[2] = 'c';
                    buffer[3] = 'e';
                    buffer[4] = 'p';
                    buffer[5] = 't';
                    buffer[6] = 'e';
                    buffer[7] = 'd';
                        }
            }

            { // mouse
                int hover; {
                    hover = length;
                    for (int i = 0; i < length; ++i) {
                        if (mouseX < i + 0.5) {
                            hover = i;
                            break;
                        }
                    }
                }

                if (mousePressed) {
                    cursor = hover;
                    select = cursor;
                }
                if (mouseHeld) {
                    select = hover;
                }
            }


            { // draw
                if (select != cursor) {
                    drawRectangle(select, -0.5, cursor, 1.5, CYAN);
                } else {
                    if ((blinkCounter++ % 14) < 7) drawLine(cursor, -0.5, cursor, 1.5, BLUE, 5.0);
                }
                _draw_set_color(BLACK);
                _set_monospaced_font_character_width(CHARACTER_WIDTH);
                _buffered_image_graphics.drawChars(buffer, 0, length, _xPIXELfromWORLD(0.0), _yPIXELfromWORLD(0.0));
            }
        }

    }
}
