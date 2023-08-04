import java.util.*;

class Main {
    // returns the length of the hypotenuse of right triangle with legs of lengths a and b
    // NOTE: please assume a and b are positive
    static double pythagoreanTheorem(double a, double b) {
        return 0.0;
    }
    
    // returns whether n is prime
    // NOTE: please assume n is positive and greater than or equal to 2
    static boolean isPrime(int n) {
        return false;
    }
    
    // returns the sum of n's (base-10) digits
    // NOTE: digitSum should NOT call itself
    static int digitSum(int n) {
        return 0;
    }
    
    // returns the sum of hex's (base-16) digits
    // NOTE: Hexadecimal works like this 0, ..., 9, A, ..., F
    //       A is 10, B is 11, ..., F is 15.
    // NOTE: do NOT have separate cases for 'A', 'B', 'C', 'D', 'E', and, 'F'
    //       instead, have only two cases:
    //       - digit (character) in range ['0', '9']
    //       - digit (character) in range ['A', 'F']
    //       - (Optional) any other digit -- print an error and return -1
    // NOTE: please assume hex is a valid hex number, e.g. "A7" or "33333"
    static int hexDigitSum(String hex) {
        return 0;
    }
    
    public static void main(String[] arguments) {
        // Examples.printStringOneCharacterAtATimeForNoReason("Hello World");
        // System.out.println(Examples.numDigits(123));
        // Helpers.grade();
    }
}



class Examples {
    static void printStringOneCharacterAtATimeForNoReason(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            System.out.print(c);
        }
        System.out.println();
    }
    
    static int numDigits(int n) {
        n = Math.abs(n); // make n positive    
        
        int result = 0;
        while (n > 0) {
            ++result;
            n /= 10;
        }
        return result;
    }
}



class Helpers {
    // Runs some basic tests and estimates your grade.
    static void grade() {
        GRADER_BEGIN_PROBLEM("pythagoreanTheorem");
        try { GRADER_ASSERT(Helpers._areApproximatelyEqual(Main.pythagoreanTheorem(3.0, 4.0), 5.0)); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Helpers._areApproximatelyEqual(Main.pythagoreanTheorem(1.0, 1.0), Math.sqrt(2))); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }

        GRADER_BEGIN_PROBLEM("isPrime");
        try { GRADER_ASSERT(Main.isPrime(7) == true); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.isPrime(2) == true); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.isPrime(37) == true); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.isPrime(55) == false); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }

        GRADER_BEGIN_PROBLEM("digitSum");
        try { GRADER_ASSERT(Main.digitSum(0) == 0); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.digitSum(22) == 4); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.digitSum(123) == 6); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.digitSum(7770) == 21); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }

        GRADER_BEGIN_PROBLEM("hexDigitSum");
        try { GRADER_ASSERT(Main.hexDigitSum("") == 0); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.hexDigitSum("22") == 4); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.hexDigitSum("F1") == 17); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        try { GRADER_ASSERT(Main.hexDigitSum("AB23") == 26); } catch (Exception exception) { GRADER_HANDLE_EXCEPTION(); }
        
        GRADER_FINISH_GRADING();
    }

    static boolean _areApproximatelyEqual(double a, double b) {
        return Math.abs(a - b) < 1.0e-5;
    }
    
    // Feel free to ignore everything below this comment. //////////////////////

    static private boolean graderReset = true;
    static private int graderCurrentProblemIndex;
    static private String gradeCurrentProblemName;
    static private boolean graderProblemCurrentlyPassing;
    static private int gradeNumProblemsPassed;
    static private ArrayList<String> graderPassedProblemNames;
    static private ArrayList<String> graderFailedProblemNames;

    static private void _GRADER_END_PROBLEM() {
        System.out.println();
        if (graderProblemCurrentlyPassing) {
            ++gradeNumProblemsPassed;
            graderPassedProblemNames.add(gradeCurrentProblemName);
        } else {
            graderFailedProblemNames.add(gradeCurrentProblemName);
        }
    }
    
    static private void GRADER_BEGIN_PROBLEM(String questionName) {
        if (graderReset) {
            graderReset = false;
            graderCurrentProblemIndex = 0;
            graderProblemCurrentlyPassing = false;
            gradeNumProblemsPassed = 0;
            gradeCurrentProblemName = null;
            graderPassedProblemNames = new ArrayList<>();
            graderFailedProblemNames = new ArrayList<>();
            System.out.println("/**");
            System.out.println(" * Helpers.grade()");
            System.out.println(" * + means test passed (function produced correct result)");
            System.out.println(" * - means test failed (function produced incorrect result)");
            System.out.println(" * x means function crashed");
            System.out.println(" *");
        } else {
            _GRADER_END_PROBLEM();
        }
        gradeCurrentProblemName = questionName;
        ++graderCurrentProblemIndex;
        graderProblemCurrentlyPassing = true;
        System.out.print(" * " + questionName + " ");
    }
    
    static private void GRADER_ASSERT(boolean shouldBeTrue) {
        System.out.print((shouldBeTrue == true) ? '+' : '-');
        graderProblemCurrentlyPassing &= shouldBeTrue;
    }
    
    static private void GRADER_HANDLE_EXCEPTION() {
        System.out.print('x');
        graderProblemCurrentlyPassing = false;
    }
    
    static private void GRADER_FINISH_GRADING() {
        if (graderCurrentProblemIndex != 0) { _GRADER_END_PROBLEM(); }
        
        int numProblems = graderCurrentProblemIndex;
        int numProblemsIncorrect = numProblems - gradeNumProblemsPassed;
        
        char grade = 'C'; {
            if (numProblemsIncorrect == 0) {
                grade = 'A';
            } else if (numProblemsIncorrect == 1) {
                grade = 'B';
            } 
        }
        
        System.out.println(" * ");
        System.out.println(" * Passed all tests " + graderPassedProblemNames);
        System.out.println(" * Failed some test " + graderFailedProblemNames);
        System.out.println(" * ");
        System.out.println(" * Grade ~ " + grade);
        System.out.println(" **/");
        
        graderReset = true;
    }
}
