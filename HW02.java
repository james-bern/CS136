class Main {
    static void simulate1DAutomaton(int rule) {
        // init
        int[] curr = new int[79];
        int[] next = new int[curr.length];
        curr[curr.length  / 2] = 1;

        for (int generation = 0; generation < 32; ++generation) {
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
        simulate1DAutomaton(22);
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



