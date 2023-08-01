// TODO: Create solution repo

// Helpers.grade() Output: TODO

import java.util.*;

class Main {
    // returns the length of the hypotenuse of right triangle with legs of lengths a and b
    static double pythagoreanTheorem(double a, double b) {
        return 0.0;
    }
    
    // returns whether n is prime
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
    static int hexDigitSumHex(String hex) {
        return 0;
    }
    
    public static void main(String[] arguments) {
        Helpers.grade();
    }
}

class Examples {
    void printStringCharacterByCharacterForNoReason(String string) {
        for (int i = 0; i < string.length(); ++i) {
            System.out.print(string.charAt(i));
        }
        System.out.println();
    }
}

class Helpers {
    
    // Runs some basic tests and estimates your grade.
    static void grade() {
        PROBLEM("isPrime");
        CHECK(Main.isPrime(7) == true);
        CHECK(Main.isPrime(2) == true);
        CHECK(Main.isPrime(37) == true);
        CHECK(Main.isPrime(55) == false);
        
        ESTIMATE_GRADE();
    }
    
    // Feel free to ignore everything below this comment. //////////////////////

    static private int problemIndex = 0;
    static private boolean problemPassingPerfectly;
    static private int numProblemsPassedPerfectly;
    static private String currentProblem;
    static private ArrayList<String> perfectProblems = new ArrayList<>();
    static private ArrayList<String> imperfectProblems = new ArrayList<>();
    static private void _END_PROBLEM() {
        if (problemPassingPerfectly) {
            ++numProblemsPassedPerfectly;
            perfectProblems.add(currentProblem);
        } else {
            imperfectProblems.add(currentProblem);
        }
    }
    
    static private void PROBLEM(String questionName) {
        currentProblem = questionName;
        if (problemIndex != 0) {
            _END_PROBLEM();
        }
        ++problemIndex;
        problemPassingPerfectly = true;
        String string = "Problem: " + questionName;
        System.out.println();
        System.out.println(string);
        for (int i = 0; i < string.length(); ++i) { System.out.print('-'); }
        System.out.println();
    }
    
    static private void CHECK(boolean shouldBeTrue) {
        if (shouldBeTrue) {
            System.out.println("+ test passed");
        } else {
            System.out.println("- test failed");
        }
        problemPassingPerfectly &= shouldBeTrue;
    }
    
    static private void ESTIMATE_GRADE() {
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
        
        System.out.println();
        System.out.println("TODO: " + imperfectProblems);
        System.out.println("Passed tests: " + perfectProblems);
        System.out.println("Estimated grade: " + grade);
    }
}
