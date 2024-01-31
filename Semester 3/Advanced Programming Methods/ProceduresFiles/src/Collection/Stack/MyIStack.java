package Collection.Stack;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.Statement.IStatement;
import Model.Value.BoolValue;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    BoolValue isEmpty();
    T peek() throws ToyLanguageInterpreterException;
    String toString();
    Stack<T> getStack();
    MyIStack<T> createStackDuplicate();
    List<T> getAll();


}