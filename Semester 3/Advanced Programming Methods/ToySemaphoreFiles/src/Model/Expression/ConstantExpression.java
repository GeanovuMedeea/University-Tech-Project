package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ConstantExpression implements Expression {
    private final Value value;
    public ConstantExpression(Value value){
        this.value = value;
    }
    public Value getNumber(){
        return value;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return value.getType();
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap){
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}