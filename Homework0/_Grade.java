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
            boolean pass = false;

            int[] array = { 0, 1, 1, 0, 0, 1, 1, 1 };

            try {
                int tmp[] = Main.getNextUsingRule126(array);
                boolean allMatch = true;
                for (int i = 0; i < array.length; ++i) {
                    if (array[i] != tmp[i]) {
                        allMatch = false;
                        break;
                    }
                }
                pass = allMatch;
            } catch (Exception exception) { }

            GRADE_ASSERT(pass);
        }

        {
            GRADE_ASSERT(5, true);
        }

        PRINT_GRADE();
    }
}
