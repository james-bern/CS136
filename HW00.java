import java.util.*;

class Main {
    public static void main(String[] arguments) {
        System.out.println("Welcome to CS136!");
        while (true) {
             System.out.println("You awaken in a dark room...");
             System.out.println("- Type 1 and press Enter to go back to sleep.");
             System.out.println("- Type 2 and press Enter to turn on the lights.");
            
            int choice = Helpers.getIntFromUser();
            if (choice == 1) {
                System.out.println("You go to sleep. It is awesome.");
            } else if (choice == 2) {
                System.out.println("Congratulations, you have turned on the lights. :) The game is now over.");
                break;
            }
        }
    }
}

class Helpers {
    // Waits for the user to type an integer and press Enter.
    // Returns the (first) integer the user typed.
    // If the user typed something other than an integer, e.g, false, prints an error and returns Integer.MIN_VALUE.
    static int getIntFromUser() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        System.out.println("[scannerNextInt] ERROR: user did not type an integer; returning Integer.MIN_VALUE.");
        return Integer.MIN_VALUE; 
    }
}
