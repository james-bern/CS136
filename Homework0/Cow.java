import java.util.Arrays;
import java.nio.CharBuffer;

class Cow {
    private Cow() {}

    static void print(int[] array) { print(Arrays.stream(array).boxed().toArray(Integer[]::new)); }
    static void print(long[] array) { print(Arrays.stream(array).boxed().toArray(Long[]::new)); }
    static void print(double[] array) { print(Arrays.stream(array).boxed().toArray(Double[]::new)); }
    static void print(char[] array) { print(CharBuffer.wrap(array).chars().mapToObj(ch -> (char)ch).toArray(Character[]::new)); }
    static <E> void print(E[] array) {
        System.out.print("{ ");
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + ", ");
        }
        System.out.println("}");
    }
}

// TODO
// class Vector {
// 
// }
