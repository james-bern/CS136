import java.util.Arrays;
import java.nio.CharBuffer;
import java.util.Collection;

class Cow {
    private Cow() {}

    static void printArray(int[] array) { printArray(Arrays.stream(array).boxed().toArray(Integer[]::new)); }
    static void printArray(long[] array) { printArray(Arrays.stream(array).boxed().toArray(Long[]::new)); }
    static void printArray(double[] array) { printArray(Arrays.stream(array).boxed().toArray(Double[]::new)); }
    static void printArray(char[] array) { printArray(CharBuffer.wrap(array).chars().mapToObj(ch -> (char)ch).toArray(Character[]::new)); }
    static <ElementType> void printArray(ElementType[] array) {
        System.out.println(arrayToString(array));
    }
    static <ElementType> String arrayToString(ElementType[] array) {
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

    final static String[] pokemon = { "Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise", "Caterpie", "Metapod", "Butterfree", "Weedle", "Kakuna", "Beedrill", "Pidgey", "Pidgeotto", "Pidgeot", "Rattata", "Raticate", "Spearow", "Fearow", "Ekans", "Arbok", "Pikachu", "Raichu", "Sandshrew", "Sandslash", "Nidoran♀", "Nidorina", "Nidoqueen", "Nidoran♂", "Nidorino", "Nidoking", "Clefairy", "Clefable", "Vulpix", "Ninetales", "Jigglypuff", "Wigglytuff", "Zubat", "Golbat", "Oddish", "Gloom", "Vileplume", "Paras", "Parasect", "Venonat", "Venomoth", "Diglett", "Dugtrio", "Meowth", "Persian", "Psyduck", "Golduck", "Mankey", "Primeape", "Growlithe", "Arcanine", "Poliwag", "Poliwhirl", "Poliwrath", "Abra", "Kadabra", "Alakazam", "Machop", "Machoke", "Machamp", "Bellsprout", "Weepinbell", "Victreebel", "Tentacool", "Tentacruel", "Geodude", "Graveler", "Golem", "Ponyta", "Rapidash", "Slowpoke", "Slowbro", "Magnemite", "Magneton", "Farfetch'd", "Doduo", "Dodrio", "Seel", "Dewgong", "Grimer", "Muk", "Shellder", "Cloyster", "Gastly", "Haunter", "Gengar", "Onix", "Drowzee", "Hypno", "Krabby", "Kingler", "Voltorb", "Electrode", "Exeggcute", "Exeggutor", "Cubone", "Marowak", "Hitmonlee", "Hitmonchan", "Lickitung", "Koffing", "Weezing", "Rhyhorn", "Rhydon", "Chansey", "Tangela", "Kangaskhan", "Horsea", "Seadra", "Goldeen", "Seaking", "Staryu", "Starmie", "Mr. Mime", "Scyther", "Jynx", "Electabuzz", "Magmar", "Pinsir", "Tauros", "Magikarp", "Gyarados", "Lapras", "Ditto", "Eevee", "Vaporeon", "Jolteon", "Flareon", "Porygon", "Omanyte", "Omastar", "Kabuto", "Kabutops", "Aerodactyl", "Snorlax", "Articuno", "Zapdos", "Moltres", "Dratini", "Dragonair", "Dragonite", "Mewtwo", "Mew" };
}

class ToyArrayList<ElementType> {
    int length;
    private ElementType[] internalArray;
    ToyArrayList() {
        this.length = 0;
        this.internalArray = (ElementType[]) (new Object[4]);
    }

    public String toString() {
        String result = "< ";
        for (int i = 0; i < length; ++i) {
            result += this.get(i) + ", ";
        }
        result += ">";
        return result;
    }

    ElementType get(int index) {
        return this.internalArray[index];
    }

    void add(ElementType element) {
        if (this.length == this.internalArray.length) { // internal array is full
            ElementType[] newInternalArray = (ElementType[]) (new Object[2 * this.internalArray.length]);
            for (int i = 0; i < this.length; ++i) {
                newInternalArray[i] = this.internalArray[i];
            }
            this.internalArray = newInternalArray;
        }
        this.internalArray[this.length++] = element;
    }



    // FORNOW
    public <T> T[] toArray(T[] array) {
        if (array.length < this.length) {
            array = (T[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), this.length);
        }
        System.arraycopy(internalArray, 0, array, 0, this.length);
        if (array.length > this.length) {
            array[this.length] = null;
        }
        return array;
    }



    public static void main(String[] args) {
        ToyArrayList<String> arrayList = new ToyArrayList<String>();
        System.out.println(arrayList); // [ null, null, null, null, ]
        System.out.println(arrayList.length);    // 0

        // [ Bulbasaur, null, null, null, ]
        // [ Bulbasaur, Ivysaur, null, null, ]
        // [ Bulbasaur, Ivysaur, Venusaur, null, ]
        // [ Bulbasaur, Ivysaur, Venusaur, Charmander, ]
        // [ Bulbasaur, Ivysaur, Venusaur, Charmander, Charmeleon, null, null, null, ]
        // ...
        // [ Bulbasaur, ..., Blastoise, null, ..., null, ]
        for (int i = 0; i < 9; ++i) {
            arrayList.add(Cow.pokemon[i]);
            System.out.println(arrayList);
        }

        System.out.println(arrayList.length); // 9
    }
}
