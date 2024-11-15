import java.io.FileWriter;

public class SymbolTable<T> {
    int currentCode;
    Node<T> root;

    public SymbolTable() {
        root = null;
        currentCode = 0;
    }

    public int insert(T key) {
        root = insertHelper(key, root);
        return find(key);
    }

    public Node<T> insertHelper(T symbol, Node<T> currentNode)
    {
        if(currentNode == null){
            Node<T> node = new Node<>(currentCode++, symbol);
            return node;
        }

        if(currentNode.getSymbol().toString().compareTo(symbol.toString())==0)
            return currentNode;

        if(currentNode.getSymbol().toString().compareTo(symbol.toString()) > 0)
            currentNode.left = insertHelper(symbol, currentNode.left);
        else if(currentNode.getSymbol().toString().compareTo(symbol.toString())<0)
            currentNode.right = insertHelper(symbol, currentNode.right);

        return currentNode;
    }

    public int find(T symbol){
        Node<T> node = findHelper(symbol, root);
        if(node != null)
            return node.getCode();
        else
            return -1;
    }

    public Node<T> findHelper(T symbol, Node<T> currentNode)
    {
        if(currentNode == null)
            return currentNode;

        if(currentNode.getSymbol().equals(symbol))
            return currentNode;

        if(currentNode.getSymbol().toString().compareTo(symbol.toString())>0)
            return findHelper(symbol, currentNode.left);

        if(currentNode.getSymbol().toString().compareTo(symbol.toString())<0)
            return findHelper(symbol, currentNode.right);

        return currentNode;
    }

    public void printSymbolTree(){
        try {
            FileWriter writer = new FileWriter("ST.out");
            inOrder(root, writer);
            writer.close();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void inOrder(Node<T> currentNode, FileWriter writer){
        if(currentNode == null)
            return;
        inOrder(currentNode.left,writer);
        //System.out.println("Code: " + currentNode.getCode() + " Symbol: " + currentNode.getSymbol().toString());
        try {
            writer.write("Code: " + currentNode.getCode() + " Symbol: " + currentNode.getSymbol().toString()+"\n");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        inOrder(currentNode.right,writer);
    }
}