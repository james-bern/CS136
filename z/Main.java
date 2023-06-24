// TODO: goal is prep one week per week of summer
// - autograde everything
// - have them actually write the data structure
//
// // TODO: you may need to let these goals go a bit.
// //       they're an interesting idea, but they may not work so well with java java java java
// //       we can revisit this, but the big challenge is that if students access the
// //       internal array directly everything is going to get real deal wonky (type erasure)
// //       - we could make a ToyDataStructure with (part of) a static toArray method
// //       - but the complication this adds is kind of nuts
// //       we could also restrict ourselves to non-generic data structures
// //       though that's a little bit sad
//
// //       finally, we could make the draw functions less exciting (i.e., for loop city)
// //       this...actually might not be the worst idea
// //       drawLine instead of drawLineStrip (ew, but also, whatever)
// //       and then they can for loop over to their heart's content
//
// //       we just have to make sure that the students declare the array private
//
// //       something to think about
// TODO: write the usage code first
// - data structures may not be the easiest way to do this
// TODO: compression oriented programming
// - this may also be a bit hard to show

import java.util.*;

// TODO: emphasis on READING code
//
//
// TODO: when to write a class
// TODO: when to write a comment
// TODO: when to write a function
//
//
// TODO: Get to know you!
// TODO: Ask them how their day is going.
//
// M - Data Structure
// W - Application
// F - Kahoot 
//
//  1 primitives (isPrime)
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


// TODO it's okay if the first couple homeworks aren't autograded
//      this is when we want to give the students feedback
//      still, it would be way better if they were autograded

//  2 arrays (1D Automaton, 2D Game of Life)
//  - NOTE: arrays are fast
//  - NOTE: arrays are fixed size
//  - for loop
//  - for-each loop
//  - PROJECT: circular buffer
//
//
//
//
//  3 objects (Vector2, some sort of game -- creative)
//  - memory model
//  - class
//  - new
//  - constructur
//  - instance  
//  - member function
//  - String
//  - int versus Integer
//  - ...generics (have them use a generic) -- something simple
//  - STRUCTURE: Vector2 (include .equals)
//  - PROJECT: Rockets?
//
//  TODO (Jim): How do we grade this homework for correctness?--It could be done by video or actually by still image. Students should pregrade their own stuff, with a rubric.
//
//
//
//  4 array-list
//  - STRUCTURE:    ToyArrayList
//  - MINI-PROJECT: 
//  - PROJECT:      Paint
//
//  Paint
//  - Is your ToyArrayList implementation correct?
//  - Did you implement 
//  - Does pressing 'X' on the keyboard flip the drawing in X?
//  - (Bonus) 
//
//  5 stacks and queues
//  - STRUCTURE: ToyStack, ToyQueue
//  - PROJECT:   ? PostScript
//
//  > TODO (Jim): Implement this homework next
//  6 hash tables
//  - STRUCTURE: ToyHashtable
//  - PROJECT:   WordGen

class ToyHashtable {
}

class TextGenerator {
    public static void main(String[] args) {
        Hashtable<String, Hashtable<Character, Integer>> frequencyTables;
    }

}

//  7 linked-list 
//  - STRUCTURE: SinglyLinkedList
//  - 


//  8 trees (CowTree, recursive DFS, iterative DFS, iterative BFS)
//
//  9 heaps (CowMaxBinaryHeap, slow heap sort)
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


