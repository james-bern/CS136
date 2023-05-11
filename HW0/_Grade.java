class Grade {

    static int questionIndex;
    static int numerator;
    static int denominator;

    static void PRINT_GRADE() {
        System.out.println("-----------");
        System.out.println("Grade = " + Math.round(100.0 * numerator / denominator) + "%");
    }

    static void GRADE_ASSERT(boolean pass) { GRADE_ASSERT(1, pass); }
    static void GRADE_ASSERT(int numPoints, boolean pass) {
        ++questionIndex;
        if (pass) { numerator += numPoints; }
        denominator += numPoints;

        System.out.println("Q" + questionIndex + " = " +
                (pass ? String.valueOf(numPoints) : "0") + " / " + numPoints);
    }

    public static void main(String[] args) {
        {
            int[] curr            = { 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0 };
            int[] solutionNext126 = { 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0 };

            boolean pass = false;
            try {
                int next[] = Main.getNextUsingRule126(curr);
                boolean allMatch = true;
                for (int i = 0; i < solutionNext126.length; ++i) {
                    if (solutionNext126[i] != next[i]) { allMatch = false; break; }
                }
                pass = allMatch;
            } catch (Exception exception) { }
            GRADE_ASSERT(95, pass);
        }

        {
            GRADE_ASSERT(5, false);
        }

        PRINT_GRADE();
    }
}
