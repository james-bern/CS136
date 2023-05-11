import java.util.Vector;

class Main {
    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5 };
        System.out.print("before being reversed: ");
        Cow.print(array);
        for (int i = 0; i < array.length / 2; ++i) {
            int j = (array.length - 1) - i;
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        System.out.print(" after being reversed: ");
        Cow.print(array);

        String string = "lorem ipsum dolor";
        System.out.println(string);

        Vector<Integer> vector = new Vector<Integer>();
        vector.add(3);
        vector.add(9);
        vector.add(81);
        System.out.print("initial contents of vector:");
        System.out.println(vector);
        vector.removeElement(3);
        System.out.print("after removing element with value 3: ");
        System.out.println(vector);
        vector.removeElementAt(0);
        System.out.println(vector);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("Ekans", 23);
        map.put("Arbok", 24);
        map.put("Jigglypuff", -1);
        map.put("Jigglypuff", 39);
    }
}
