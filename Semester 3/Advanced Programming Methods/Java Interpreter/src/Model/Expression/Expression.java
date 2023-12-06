package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Model.Exceptions.*;
import Model.Type.Type;
import Model.Value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap) throws ToyLanguageInterpreterException;
    String toString();
}