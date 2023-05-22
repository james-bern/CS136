class Grade {
    static int questionIndex;
    static int numerator;
    static int denominator;

    static void PRINT_GRADE() {
        System.out.println("----------------------------");
        System.out.println();
        System.out.println("Tentative Grade = " + Math.round(100.0 * numerator / denominator) + "%");
    }

    static void GRADE_ASSERT(int numPoints, boolean pass, String description, String messageIfIncorrect) {
        if (pass) { numerator += numPoints; }
        denominator += numPoints;

        System.out.print("(" + numPoints + " points) " + Character.toString('a' + questionIndex) + ". ");
        if (description != null) {
            System.out.println(description);
        } else {
            System.out.println("");
        }

        if (pass) {
            System.out.println("+ Woo! :D");
        } else {
            System.out.print("- Test failed");
            if (messageIfIncorrect != null) {
                System.out.println(": " + messageIfIncorrect);
            }
        }

        System.out.println();

        ++questionIndex;
    }

    public static void main(String[] args) {
        {
            int[] curr            = { 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0 };
            int[] solutionNext126 = { 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0 };

            boolean pass = false;
            try {
                // int next[] = Main.getNextUsingRule126(curr);
                // boolean allMatch = true;
                // for (int i = 0; i < solutionNext126.length; ++i) {
                //     if (solutionNext126[i] != next[i]) { allMatch = false; break; }
                // }
                // pass = allMatch;
            } catch (Exception exception) { }
            GRADE_ASSERT(95, pass,
                    "Checking rule 126 implementation.",
                    "Try running through your code on a piece of paper for a simple automaton.");
        }

        {
            GRADE_ASSERT(5, false, "Checking extra credit.", "Here is a resource on binary numbers: TODO.html");
        }

        PRINT_GRADE();
    }
}
