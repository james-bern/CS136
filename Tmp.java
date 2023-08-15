   class Main {
       public static void main(String[] arguments) {
           int a = 0;
           {
               int a = 5;
           }
           System.out.println(a);
       }
   }