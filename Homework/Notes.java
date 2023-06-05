// Java -CustomScript-> Markdown -Pandoc-> Powerpoint
import java.util.*;
import java.io.*;

class Notes {
    public static void main(String[] args) {
        // TODO: notes 0, notes 1, ...
        // TODO: support /* */
        // TODO: base functionality parser
        // TODO: tables of code options
        // TODO: strip /*, */ out of the code entirely /* ... */ -> 


// FORNOW: assume that the first line of code is never just a {
// NOTE: this is usually a fine assumption, though not for the for-while loop comparison
// (We can hack those in manually, or escape with a double {{)


// BEGIN ////////////////////////////////////////////////////////////


        { // this
            // - the keyword `this` blah blah blah
            // -- not to be trusted!
            // --- asdf
            // -- foobar
            {
                class Foo {
                    int bar;
                    int cat;
                    Foo(int bar) {
                        this.bar = bar; // "this." is necessary to set this.bar
                        this.cat = 123; // "this." is optional  to set this.cat
                    }
                }
            }
        }

        { // for loop
            // - a for loop
            //    - blah blah blah
            {
                {
                    for (int i = 0; i < 10; ++i) {
    
                    }
                }
                {
                    int i = 0;
                    while (i < 10) {
    
                        ++i;
                    }
                }
            }
        }
    
// END ////////////////////////////////////////////////////////////
        { // for loop
            // - a for loop
            //    - blah blah blah
            {
                {
                   {
                        for (int i = 0; i < 10; ++i) {
                            /* ... */
                        }
                    }
                    {
                        {{
                            int i = 0;
                            while (i < 10) {
                                /* ... */
                                ++i;
                            }
                        }}
                    }
                }
                {
                    /* {
                        for (;;) {
                            ...
                        }
                    } */
                    /* {
                        while (true) {
                            ...
                        }
                    } */
                }
            }
        }

        { // approximately equal (Cow.approximatelyEqual)
        }

        { // primitive type
            // - `int i;`
            //     - `i` does NOT "refer to an integer"; `i` IS an integer
            int a = 7; // a is an integer with value 7
            int b = a; // b is a totally different integer also with value 7
            ++a; // a is now an integer with value 8
            Cow.ASSERT(a == 8);
            Cow.ASSERT(b == 7);
        }

        { // reference type
          // - Vector2 s refers to a Vector2
            ArrayList<Integer> a = new ArrayList<>(); // a refers to a newly created ArrayList<Integer>
            ArrayList<Integer> b = a; // b refers to the same ArrayList as a; no new ArrayList was created
            a.add(42); // the array list referred to by a and b now has size equal to 1
            Cow.ASSERT(a.size() == 1);
            Cow.ASSERT(b.size() == 1);
        }
    }
}


class Export {
    public static void dump(String string) {
        System.out.println(string);
    }
    public static void main(String[] args) {
        Scanner scanner = Cow.fileScanner("Notes.java");
        int state = 0;
        int tableState = 0;

        boolean firstLineOfCode = false;
        int numSpacesToStrip = -1;

        // states
        // - STATE_NOTES
        // - STATE_OTHER
        // TODO: curlies
        // TODO: close <

        int codeCurlyBraces = 0; // TODO: use this instead of abusing state
        int numPureOpeningCurlyBraces = 0;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if ((state == 0) && line.contains("BEGIN")) {
                state = 1;
                continue;
            }
            if (state != 0) {
                if (line.contains("END")) { break; }

                if (state == 1) {
                    if (line.contains("{ //")) {
                        dump("## " + line.split("// ")[1]);
                        state = 2;
                    }
                } else if (state == 2) {

                    if (line.contains("//") && !line.contains("{")) {
                        // bulleted notes
                        dump(line.split("// ")[1]
                                .replace("-", "*")
                                .replace("**", "    *")
                                .replace("**", "    *")
                                .replace("**", "    *"));
                    } else if (line.strip().equals("{")) { // beginning of code

                        ++numPureOpeningCurlyBraces; // TODO

                        dump("```java");
                        state = 3;
                        firstLineOfCode = true;
                    }
                } else if (state >= 3) {
                    if (line.contains("{")) { state++; }
                    if (line.contains("}")) { state--; }
                    if (state != 2) {
                        if (firstLineOfCode) {
                            firstLineOfCode = false;

                            { // numSpacestoStrip
                                for (
                                        numSpacesToStrip = 0;
                                        ((numSpacesToStrip < line.length()) && line.charAt(numSpacesToStrip) == ' ');
                                        ++numSpacesToStrip) {}
                            }
                        }

                        { // write code line
                            // FORNOW
                            if (numSpacesToStrip < line.length()) {
                                dump(line.substring(numSpacesToStrip));
                            } else {
                                dump("");
                            }
                        }


                    } else {
                        dump("```");
                    }
                }
            }
        }
    }
}
