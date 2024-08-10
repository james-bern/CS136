class HW02A extends Cow {
    public static void main(String[] arguments) {

        final char[] buffer = new char[8];
        int length = 0;
        int cursor = 0;
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
                    for (int i = length; i > cursor; --i) buffer[i] = buffer[i - 1];
                    buffer[cursor] = newCharacter;
                    ++cursor;
                    ++length;
                }
            }

            if (backspaceValid && keyPressed(BACKSPACE)) {
                for (int i = cursor; i < length; ++i) buffer[i - 1] = buffer[i];
                --cursor;
                --length;
            }

            if (leftArrowKeyValid && keyPressed(LEFT_ARROW_KEY)) {
                --cursor;
            }

            if (rightArrowKeyValid && keyPressed(RIGHT_ARROW_KEY)) {
                ++cursor;
            }

            if (mousePressed) {
                cursor = length;
                for (int i = 0; i < length; ++i) {
                    if (mouseX < i + 0.5) {
                        cursor = i;
                        blinkCounter = 0;
                        break;
                    }
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

            { // draw
                if ((blinkCounter++ % 14) < 7) drawLine(cursor, -0.5, cursor, 1.5, PURPLE, 5.0);
                _draw_set_color(BLACK);
                _set_monospaced_font_character_width(CHARACTER_WIDTH);
                _buffered_image_graphics.drawChars(buffer, 0, length, _xPIXELfromWORLD(0.0), _yPIXELfromWORLD(0.0));
            }
        }

    }
}
