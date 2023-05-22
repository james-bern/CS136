import java.util.Arrays;
import java.nio.CharBuffer;

class Cow {
    private Cow() {}

    static void printArray(int[] array) { printArray(Arrays.stream(array).boxed().toArray(Integer[]::new)); }
    static void printArray(long[] array) { printArray(Arrays.stream(array).boxed().toArray(Long[]::new)); }
    static void printArray(double[] array) { printArray(Arrays.stream(array).boxed().toArray(Double[]::new)); }
    static void printArray(char[] array) { printArray(CharBuffer.wrap(array).chars().mapToObj(ch -> (char)ch).toArray(Character[]::new)); }
    static <E> void printArray(E[] array) {
        System.out.println(arrayToString(array));
    }
    static <E> String arrayToString(E[] array) {
        String ret = "[ ";
        for (int i = 0; i < array.length; ++i) {
            ret += array[i] + ", ";
        }
        ret += "]";
        return ret;
    }

    void ASSERT(boolean b) {
        System.out.println("Assert.");
        System.exit(1);
    }
}

// TODO
// class Vector {
// 
// }
