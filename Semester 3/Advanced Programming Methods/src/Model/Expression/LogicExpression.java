package Model.Expression;

import Collection.Heap.MyIHeap;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Collection.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private BinaryOperationSelection operation;
    public LogicExpression(Expression exp1, Expression exp2, BinaryOperationSelection operation) {
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = operation;
    }

    private BoolValue getValue(Expression expression, MyIDictionary<String, Value> symTable,MyIHeap<Integer,Value> heap) throws ToyLanguageInterpreterException {
        Value value = expression.evaluate(symTable,heap);
        if (value instanceof BoolValue)
            return (BoolValue) value;
        throw new ExpressionException(value.toString() + " is not of type BoolValue");
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap) throws ToyLanguageInterpreterException {
        BoolValue leftValue = getValue(expression1, symbolTable,heap);
        BoolValue rightValue = getValue(expression2, symbolTable,heap);
        if(!(leftValue instanceof BoolValue))
            throw new ExpressionException(leftValue.toString() + " is not of BoolValue!");
        if(!(rightValue instanceof BoolValue))
            throw new ExpressionException(rightValue.toString() + " is not of BoolValue!");
        return switch (operation) {
            case AND -> new BoolValue(leftValue.getVal() && rightValue.getVal());
            case OR -> new BoolValue(leftValue.getVal() || rightValue.getVal());
            default -> throw new ToyLanguageInterpreterException(operation +" is an invalid operator for " + expression1.toString() + " and " + expression2.toString());
        };
    }
    @Override
    public String toString() {
        return String.format("BinaryExpression{ " + expression1.toString() + " " + operation + " " + expression2.toString()+ " }");
    }
}