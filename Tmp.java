/**
 * Helpers.grade()
 * + means test passed (function produced correct result)
 * - means test failed (function produced incorrect result)
 * x means function crashed
 *
 * pythagoreanTheorem --
 * isPrime x+++
 * digitSum +---
 * hexDigitSum +---
 * 
 * Passed all tests []
 * Failed some test [pythagoreanTheorem, isPrime, digitSum, hexDigitSum]
 * 
 * Grade ~ C
 **/

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
        for (int i = 2; i <= Math.sqrt(n); ++i) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
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
        int a = 0x7B;
        assert false;
        System.out.println(a);

        //Examples.printStringOneCharacterAtATimeForNoReason("Hello World");
        //System.out.println(Examples.numDigits(123));
        //Helpers.grade();
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
        n = Math.abs(n); // in case n is negative
        
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
        PROBLEM("pythagoreanTheorem");
        try { CHECK(Helpers.approximatelyEqualTo(Main.pythagoreanTheorem(3.0, 4.0), 5.0)); } catch (Exception exception) { CRASH(); }
        try { CHECK(Helpers.approximatelyEqualTo(Main.pythagoreanTheorem(1.0, 1.0), Math.sqrt(2))); } catch (Exception exception) { CRASH(); }

        PROBLEM("isPrime");
        try { CHECK(Main.isPrime(7) == true); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.isPrime(2) == true); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.isPrime(37) == true); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.isPrime(55) == false); } catch (Exception exception) { CRASH(); }

        PROBLEM("digitSum");
        try { CHECK(Main.digitSum(0) == 0); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.digitSum(22) == 4); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.digitSum(123) == 6); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.digitSum(7770) == 21); } catch (Exception exception) { CRASH(); }

        PROBLEM("hexDigitSum");
        try { CHECK(Main.hexDigitSum("") == 0); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.hexDigitSum("22") == 4); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.hexDigitSum("F1") == 17); } catch (Exception exception) { CRASH(); }
        try { CHECK(Main.hexDigitSum("AB23") == 26); } catch (Exception exception) { CRASH(); }
        
        DONE_GRADING();
    }

    static boolean approximatelyEqualTo(double a, double b) {
        return Math.abs(a - b) < 1.0e-5;
    }
    
    // Feel free to ignore everything below this comment. //////////////////////

    static private int problemIndex = 0;
    static private boolean problemPassingPerfectly;
    static private int numProblemsPassedPerfectly;
    static private String currentProblem;
    static private ArrayList<String> perfectProblems = new ArrayList<>();
    static private ArrayList<String> imperfectProblems = new ArrayList<>();
    static private void _END_PROBLEM() {
        System.out.println();
        if (problemPassingPerfectly) {
            ++numProblemsPassedPerfectly;
            perfectProblems.add(currentProblem);
        } else {
            imperfectProblems.add(currentProblem);
        }
    }
    
    static private void PROBLEM(String questionName) {
        if (problemIndex == 0) {
            System.out.println("/**");
            System.out.println(" * Helpers.grade()");
            System.out.println(" * + means test passed (function produced correct result)");
            System.out.println(" * - means test failed (function produced incorrect result)");
            System.out.println(" * x means function crashed");
            System.out.println(" *");
        } else {
            _END_PROBLEM();
        }
        currentProblem = questionName;
        ++problemIndex;
        problemPassingPerfectly = true;
        System.out.print(" * " + questionName + " ");
    }
    
    static private void CHECK(boolean shouldBeTrue) {
        System.out.print((shouldBeTrue == true) ? '+' : '-');
        problemPassingPerfectly &= shouldBeTrue;
    }
    
    static private void CRASH() {
        System.out.print('x');
        problemPassingPerfectly = false;
    }
    
    static private void DONE_GRADING() {
        if (problemIndex != 0) { _END_PROBLEM(); }
        
        int numProblems = problemIndex;
        int numProblemsIncorrect = numProblems - numProblemsPassedPerfectly;
        
        char grade; {
            if (numProblemsIncorrect == 0) {
                grade = 'A';
            } else if (numProblemsIncorrect == 1) {
                grade = 'B';
            } else {
                grade = 'C';
            }
        }
        
        System.out.println(" * ");
        System.out.println(" * Passed all tests " + perfectProblems);
        System.out.println(" * Failed some test " + imperfectProblems);
        System.out.println(" * ");
        System.out.println(" * Grade ~ " + grade);
        System.out.println(" **/");
    }
}
