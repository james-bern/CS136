// TODO: emphasis on READING code
//
//
// TODO: when to write a class
// TODO: when to write a comment
// TODO: when to write a function
//
//
//
// M - Data Structure
// W - Application
// F - Kahoot 
//
//
// The exams are meant to test understanding not memorization.
// Specifically
// - Did you understand the in-class Kahoot?
// - Did you understand the assigned programming homework?
// - 
//
//
// TODO: Get to know you!
// TODO: Ask them how their day is going.
//
//
//  0 primitives (isPrime)
//  - boolean
//  -- true
//  -- false
//  -- &&
//  -- ||
//  -- !
//  - int
//  -- % 10
//  -- % 2
//  - double
//  -- Math.Abs
//  -- POSITIVE_INFINITY, NEGATIVE_INFINITY
//  -- Math.Abs(a - b) < .0001 (floating point equality)
//
//
//  1 arrays (1D Automaton, 2D Game of Life)
//  - NOTE: arrays are fast
//  - NOTE: arrays are fixed size
//  - for loop
//  - for-each loop
//
//
//  2 objects (Vector2, some sort of game)
//  - memory model
//  - class
//  - new
//  - constructur
//  - instance  
//  - member function
//  - String
//  - generics

//
//  3 array-list (CowArrayList, ???)
//






//
//  4 stacks and queues (CowStack, CowQueue)
//
//
//  5 hash tables (CowHashtable, TextGenerator)
//
//
//  6 linked-list (CowLinkedList, ???)
//
//
//  7 trees (CowTree, recursive DFS, iterative DFS, iterative BFS)
//
//
//  8 heaps (CowMaxBinaryHeap, slow heap sort)
//
//
//  9 advanced topics -- recursion, implicit heaps
//  
//
// 10 JAVA JAVA JAVA JAVA iterators, object orientation, programming paradigms


class Arrays {
    static int findIndexOfMinElement(double[] array) {
        int indexOfMinElement = -1;
        double valueOfMinElement = Double.POSITIVE_INFINITY;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] < valueOfMinElement) {
                indexOfMinElement = i;
                valueOfMinElement = array[i];
            }
        }
        return indexOfMinElement;
    }

    static int findIndexOfMaxElement(double[] array) {
        // HINT: See previous function for inspiration.
        return -1;
    }



    static double[] reverseOutOfPlace(double[] array) {
        double[] ret = new double[array.length];
        // TODO: Copy array's values into ret (in reverse order).
        // NOTE: do NOT modify the original array

        return ret;
    }

    static void reverseInPlace(double[] array) {
        // TODO: Reverse the entries of array using swaps
        // NOTE: Swapping two entries will use a temporary variable,
        //       e.g., called tmp
        // NOTE: do NOT create a new array; DO modify the original array

    }




    static boolean isPrimeBruteForce(int a) {
        return false;
    }

    static boolean areAllPrime(int[] array) {
        return false;
    }

    static boolean areAnyPrime(int[] array) {
        return false;
    }




    static boolean contains(int[] array, int target) {
        return false;
    }


    // ?
    double[] mergeTwoSortedArrays(double[] A, double[] B) {

        return null;
    }


    public static void main(String[] args) {
        // TODO (Jim): Write test functions
    }
}

class Automaton {
    int[] array;

    Automaton() {
        this.array = new int[79];
        this.array[this.array.length  / 2] = 1;
    }

    void print() {
        for (int i = 0; i < this.array.length; ++i) {
            System.out.print((this.array[i] == 0) ? " " : "X");
        }
        System.out.println();
    }

    void update() {
        int[] next = new int[this.array.length];
        // NOTE: The entries of next start out initialized to zero.
        // NOTE: What to do at the boundaries is a "design decision" (no right answer).
        //       It is A-OK to keep next[i] and next[this.array.length - 1] as zero.

        // TODO: Use the data inside of this.array to set the data inside of next.
        // NOTE: You should NOT modify the data inside of this.array (Why?)
        // HINT: A very stylish solution is ~6 lines long.
        {
            for (int i = 1; i < next.length - 1; ++i) {
                int count = this.array[i - 1] + this.array[i] + this.array[i + 1];
                if (count == 1 || count == 2) {
                    next[i] = 1;
                }
            }
        }

        this.array = next;
    }

    public static void main(String[] args) {
        // NOTE: This code tests the Automaton class.
        Automaton automaton = new Automaton();
        automaton.print();
        for (int i = 0; i < 31; ++i) {
            automaton.update();
            automaton.print();
        }
    }
}

/*
 * FUNdaMENTALs
 */


