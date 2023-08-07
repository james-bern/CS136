class Main {
    static void simulate1DAutomaton(int rule) {
        // init
        int[] curr = new int[79];
        int[] next = new int[curr.length];
        curr[curr.length  / 2] = 1;

        for (int generation = 0; generation < 16; ++generation) {
            { // print
                String string = "";
                for (int i = 0; i < curr.length; ++i) {
                    if (curr[i] == 0) {
                        string += ' ';
                    } else {
                        string += 'X';
                    }
                }
                System.out.println(string);
            }

            { // update
                // build next
                for (int i = 1; i < curr.length - 1; ++i) {
                    int count = curr[i - 1] + curr[i] + curr[i + 1];
                    if (count == 1) {
                        next[i] = 1;
                    } else {
                        next[i] = 0;
                    }
                }

                // curr <- next
                for (int i = 0; i < curr.length; ++i) curr[i] = next[i];
            }

        }
    }

    public static void main(String[] arguments) {
        while (true) {
            simulate1DAutomaton(22);

            sleepThenClearTerminal(100);
        }     
    }

    // You must run your program in a regular Terminal for this function to work.
    // Our Documentation describes how to do this. 
    static void sleepThenClearTerminal(int millisecondsToSleep) {
        try {
            Thread.sleep(millisecondsToSleep);
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else Runtime.getRuntime().exec("clear");
        } catch (Exception exception) {
        }
    }

}    



// init carl
int[] carl = new int[79];
carl[carl.length  / 2] = 1;
int[] carlWorkArray = new int[carl.length];

// init paul
int[] paul = new int[53];
paul[paul.length  / 2] = 1;
int[] paulWorkArray = new int[paul.length];

// simulate carl
for (int generation = 0; generation < 16; ++generation) {
    { // print carl
        String string = "";
        for (int i = 0; i < carl.length; ++i) {
            if (carl[i] == 0) {
                string += ' ';
            } else {
                string += 'X';
            }
        }
        System.out.println(string);
    }

    { // update carl
        // build carlWorkArray
        for (int i = 1; i < carl.length - 1; ++i) {
            int count = carl[i - 1] + carl[i] + carl[i + 1];
            if (count == 1) {
                carlWorkArray[i] = 1;
            } else {
                carlWorkArray[i] = 0;
            }
        }

        // carl <- carlWorkArray
        for (int i = 0; i < carl.length; ++i) carl[i] = carlWorkArray[i];
    }
}

// simulate paul
for (int generation = 0; generation < 24; ++generation) {
    { // print paul
        String string = "";
        for (int i = 0; i < paul.length; ++i) {
            if (paul[i] == 0) {
                string += ' ';
            } else {
                string += 'X';
            }
        }
        System.out.println(string);
    }

    { // update paul
        // build paulWorkArray
        for (int i = 1; i < paul.length - 1; ++i) {
            int count = paul[i - 1] + paul[i] + paul[i + 1];
            if (count == 1) {
                paulWorkArray[i] = 1;
            } else {
                paulWorkArray[i] = 0;
            }
        }

        // paul <- paulWorkArray
        for (int i = 0; i < paul.length; ++i) paul[i] = paulWorkArray[i];
    }
}

