package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Model.Exceptions.ADTEmptyException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Type.Type;
import Model.Value.Value;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws VariableNotFoundException {
        return typeEnv.get(name);
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap) throws VariableNotFoundException {
        if(symbolTable.containsKey(name))
            return symbolTable.get(name);
        else throw new VariableNotFoundException("Variable not found in symtable!");
    }

    @Override
    public String toString() {
        return name;
    }
}