import java.util.*;

// NOTE: Once you understand how this starter code works,
//       you will want to delete/change most of it. :)

class FlipBook extends App {
    
    ArrayList<Vector2> foo;
    
    void setup() {
        System.out.println("Press Q to quit.");
        System.out.println("Press R to rerun setup().");
        
        foo = new ArrayList<Vector2>();
        foo.add(new Vector2(0.0, 0.0));
        foo.add(new Vector2(0.0, 1.0));
    }
    
    void loop() {
        if (mousePressed) { System.out.println("Mouse pressed."); }
        if (mouseHeld) { System.out.println("Mouse held."); }
        if (mouseReleased) { System.out.println("Mouse released."); }
        if (keyPressed('S')) { System.out.println("Key S pressed."); }
        if (keyToggled('P')) { System.out.println("Key P toggled."); }
        
        drawLine(foo.get(0), foo.get(1), Vector3.black);
        drawLine(foo.get(1), mousePosition, Vector3.black);
    }
    
    public static void main(String[] arguments) {
        App app = new FlipBook();
        app.setWindowBackgroundColor(1.0, 1.0, 1.0);
        app.setWindowSizeInWorldUnits(8.0, 8.0);
        app.setWindowCenterInWorldUnits(0.0, 0.0);
        app.setWindowHeightInPixels(512);
        app.setWindowTopLeftCornerInPixels(64, 64);
        app.run();
    }
    
}