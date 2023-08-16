import java.util.*;

class Main {
    
    static class Token {
        int type;
        private boolean valueIfTypeBoolean;
        private double valueIfTypeDouble;
        private String valueIfTypeString;
        
        static final int TYPE_BOOLEAN = 0;
        static final int TYPE_DOUBLE  = 1;
        static final int TYPE_STRING  = 2;   
        
        Token(boolean value) { type = TYPE_BOOLEAN; valueIfTypeBoolean = value; } 
        Token(double  value) { type = TYPE_DOUBLE;  valueIfTypeDouble  = value; }
        Token(String  value) { type = TYPE_STRING;  valueIfTypeString  = value; }
        
        public String toString() {
            if (type == TYPE_BOOLEAN) { return "" + valueIfTypeBoolean; }
            if (type == TYPE_DOUBLE ) { return " " + valueIfTypeDouble;  }
            if (type == TYPE_STRING ) { return " " + valueIfTypeString;  }
            assert false;
            return "";
        }
        
        boolean getBoolean() { assert type == TYPE_BOOLEAN; return valueIfTypeBoolean; }
        double  getDouble()  { assert type == TYPE_DOUBLE;  return valueIfTypeDouble;  }
        String  getString()  { assert type == TYPE_STRING;  return valueIfTypeString;  }
    }
    
    static Stack<Token> _stack = new Stack<>();
    static Token   stackPopToken() { assert _stack.size() > 0; return _stack.pop(); }
    static boolean stackPopBoolean() { Token token = stackPopToken(); return token.getBoolean(); }
    static double  stackPopDouble()  { Token token = stackPopToken(); return token.getDouble();  }
    static String  stackPopString()  { Token token = stackPopToken(); return token.getString();  }
    static void stackPush(Token token) { _stack.push(token); }
    static void stackPush(boolean value) { _stack.push(new Token(value)); }
    static void stackPush(double value)  { _stack.push(new Token(value)); }
    static void stackPush(String value)  { _stack.push(new Token(value)); }
    static void stackPrint() {
        ArrayList<Token> tmp = new ArrayList<>();
        for (Token token : _stack) { tmp.add(token); }
        for (int i = tmp.size() - 1; i >= 0; --i) { System.out.println(tmp.get(i)); }
        System.out.println("-----");
        System.out.println("stack");
    }
    
    static ArrayList<Token> getTokens(String postScriptProgram) {
        ArrayList<Token> result = new ArrayList<>();
        Scanner scanner = new Scanner(postScriptProgram);
        while (scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                result.add(new Token(scanner.nextDouble()));
            } else if (scanner.hasNextBoolean()) {
                result.add(new Token(scanner.nextBoolean()));
            } else {
                String string = scanner.next().trim();
                if (string.length() != 0) {
                    result.add(new Token(string));
                }
            }
        }
        return result;
    }
    
    public static void main(String[] arguments) {
        boolean DEBUG = true;
        
        for (Token token : getTokens("4.0 2.0 add 1.0 sub 5.0 eq")) {
            if (DEBUG) { System.out.println("> " + token + "\n"); }
            
            if (token.type == Token.TYPE_DOUBLE) {
                stackPush(token);
            } else if (token.type == Token.TYPE_STRING) {
                String string = token.getString();
                if (string.equals("pstack")) {
                    stackPrint();
                } else {
                    double B = stackPopDouble();
                    double A = stackPopDouble();
                    if      (string.equals("add")) { stackPush(A + B);  }
                    else if (string.equals("sub")) { stackPush(A - B);  }
                    else if (string.equals("eq" )) { stackPush(A == B); }
                    else if (string.equals("neq")) { stackPush(A != B); }
                    else { /* TODO: symbols */ }
                }
            }
            
            if (DEBUG) { stackPrint(); System.out.println(); }
        }
    }
}