public class Main {
    public static void main(String[] args) {

        SymbolTable<Object> st = new SymbolTable<>();
        PIF pif = new PIF();

        Scanner scanner = new Scanner(st,pif);
        scanner.scan("p1simple.in");
        //System.out.println("Code for 'ana': " + st.insert("ana"));
        //System.out.println("Code for 'mimi': " + st.insert("mimi"));
        //System.out.println("Code for 'aero': " + st.insert("aero"));
        //System.out.println("Code for 'chrono': " + st.insert("chrono"));
        //.out.println("Code for 'vina': " + st.insert("vina"));
        //System.out.println("Code for 'aci': " + st.insert("aci"));
        //System.out.println("Code for 'aci': " + st.insert("aci"));
        //System.out.println("Code for 'aci': " + st.insert("aci"));
        //System.out.println("Code for 'aero': " + st.insert("aero"));

        //st.printSymbolTree();
    }
}
