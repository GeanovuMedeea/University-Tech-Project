package Collection.Stack;

import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Value.BoolValue;
import Model.Value.IntValue;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T v)
    {
        stack.push(v);
    }

    @Override
    public BoolValue isEmpty()
    {
        return new BoolValue(stack.empty());
    }

    @Override
    public String toString(){
        String result = "";
        int stackLenght = this.stack.size();
        MyIStack<T> tempStack = createStackDuplicate();
        for(T element:tempStack.getStack()) {
            result = result + "Step " + stackLenght + ": " + element.toString() + "\n";
            stackLenght--;
        }

        return result;
        // return stack.toString();
    }
    @Override
    public T peek() throws ToyLanguageInterpreterException{
        if (stack.isEmpty())
            throw new ToyLanguageInterpreterException("STACK ERROR: Stack is empty");
        return stack.peek();
    }
    public Stack<T> getStack(){
        return this.stack;
    }

    public MyIStack<T> createStackDuplicate(){
        MyIStack<T> stackTemporary = new MyStack<>();
        for(T elem : this.stack)
            stackTemporary.push(elem);
        return stackTemporary;
    }

}
