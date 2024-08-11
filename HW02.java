import java.awt.*;
class HW02 extends Cow {
    public static void main(String[] arguments) {

        char[] buffer = new char[8];
        int length = 0;
        int cursor = 0;

        canvasConfig(0.0, -1.0, buffer.length, 2.0, WHITE, 512);
        while (beginFrame()) {

            // TODO

            drawTextHW02(buffer, length, BLACK);
        }
    }

    static void drawTextHW02(char[] buffer, int length, Color color) {
        _draw_set_color(color);
        _set_monospaced_font_character_width(1.0);
        _buffered_image_graphics.drawChars(buffer, 0, length, _xPIXELfromWORLD(0.0), _yPIXELfromWORLD(0.0));
    }
}

