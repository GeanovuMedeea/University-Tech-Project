public class Node<T> {
    private T symbol;
    private int code;
    Node<T> left, right;

    public Node(int code, T symbol) {
        this.code = code;
        this.symbol = symbol;
        this.left = this.right = null;
    }

    T getSymbol(){
        return this.symbol;
    }

    int getCode(){
        return code;
    }

    Node<T> getLeft()
    {
        return this.left;
    }

    Node<T> getRight(){
        return this.right;
    }
}
