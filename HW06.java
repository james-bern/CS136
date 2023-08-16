import java.util.*;

class Main {
    
    static class Token {
        int type;
        private boolean valueIfTypeBoolean;
        private double  valueIfTypeDouble;
        private String  valueIfTypeString;
        
        static final int TYPE_BOOLEAN    = 0;
        static final int TYPE_DOUBLE     = 1;
        static final int TYPE_STRING     = 2;
        static final String[] _typeNames = { "boolean", "double", "String" };
        static String stringFromType(int type) { return _typeNames[type]; }
        
        Token(boolean value) { type = TYPE_BOOLEAN; valueIfTypeBoolean = value; } 
        Token(double  value) { type = TYPE_DOUBLE;  valueIfTypeDouble  = value; }
        Token(String  value) { type = TYPE_STRING;  valueIfTypeString  = value; }
        
        public String toString() {
            if (type == TYPE_BOOLEAN) { return "" + valueIfTypeBoolean; }
            if (type == TYPE_DOUBLE ) { return "" + valueIfTypeDouble;  }
            if (type == TYPE_STRING ) { return "" + valueIfTypeString;  }
            assert false : "Token type not recognized; Token type = " + type;
            return "";
        }
        
        boolean getBoolean() { assert type == TYPE_BOOLEAN : "Token type is " + stringFromType(type); return valueIfTypeBoolean; }
        double  getDouble()  { assert type == TYPE_DOUBLE  : "Token type is " + stringFromType(type); return valueIfTypeDouble;  }
        String  getString()  { assert type == TYPE_STRING  : "Token type is " + stringFromType(type); return valueIfTypeString;  }
        
        static ArrayList<Token> tokenize(String postScriptProgram) {
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
    }
    
    static class Interpreter {
        // NOTE: do not access _stack directly (but you can inspect it in the debugger!)
        //       instead, use stackPop*(), stackPush(...), and stackPrint()
        Stack<Token> _stack; 

        // NOTE: do not access _symbolTable directly (but you can inspect it in the debugger!)
        // NOTE: instead, use symbolTablePut() and symbolTableGet()
        HashMap<String, Token> _symbolTable;
        
        
        Interpreter() {
            _stack = new Stack<>();
            _symbolTable = new HashMap<>();
        }
        
        Token   stackPopToken()   { assert _stack.size() > 0 : "can't pop; stack is empty"; return _stack.pop(); }
        boolean stackPopBoolean() { Token token = stackPopToken(); return token.getBoolean(); }
        double  stackPopDouble()  { Token token = stackPopToken(); return token.getDouble();  }
        String  stackPopString()  { Token token = stackPopToken(); return token.getString();  }
        
        void stackPush(Token   token) { _stack.push(token);            }
        void stackPush(boolean value) { _stack.push(new Token(value)); }
        void stackPush(double  value) { _stack.push(new Token(value)); }
        void stackPush(String  value) { _stack.push(new Token(value)); }
        
        void stackPrint() {
            ArrayList<Token> tmp = new ArrayList<>();
            for (Token token : _stack) { tmp.add(token); }
            for (int i = tmp.size() - 1; i >= 0; --i) { System.out.println(tmp.get(i)); }
            System.out.println("-----");
            System.out.println("stack");
        }
        
        void symbolTablePut(String key, Token value) {
            // TODO: assert?
        }
        
        Token symbolTableGet(String key) {
            // TODO: assert
        }
        
        void interpret(ArrayList<Token> tokens, boolean PRINT_DEBUG_INFO) {
            for (Token token : tokens) {
                if (PRINT_DEBUG_INFO) { System.out.println("> " + token + "\n"); }
                
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
                
                if (PRINT_DEBUG_INFO) { stackPrint(); System.out.println(); }
            }
        }
    }
    
    public static void main(String[] arguments) {
        Interpreter interpreter = new Interpreter();
        ArrayList<Token> tokens = Token.tokenize("4.0 2.0 add 1.0 sub 5.0 eq pstack");
        interpreter.interpret(tokens, false);
    }
}