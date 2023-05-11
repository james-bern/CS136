class Main {
    static void print(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print((array[i] != 0) ? "X" : " ");
        }
        System.out.println();
    }

    static int[] getNextUsingRule126(int[] curr) {
        int[] next = new int[curr.length];
        for (int i = 1; i < curr.length - 1; ++i) { // NOTE: don't update boundary
            int count = curr[i - 1] + curr[i] + curr[i + 1];
            if (count == 1 || count == 2) {
                next[i] = 1;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        int[] array = new int[79];
        array[array.length / 2] = 1;
        print(array);

        for (int i = 0; i < 64; ++i) {
            array = getNextUsingRule126(array);
            print(array);
        }
    }
}
