package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expression;

    public ReadHeapExpression(Expression expressionToRead) {
        expression = expressionToRead;
    }

    private RefValue getValue(Expression expression, MyIDictionary<String, Value> symTable, MyIHeap<Integer,Value> heap) throws ExpressionException, ToyLanguageInterpreterException {
        Value value = expression.evaluate(symTable,heap);
        //System.out.println("getValue " + value.toString());
        if (value instanceof RefValue)
            return (RefValue) value;
        throw new ExpressionException(value.toString() + " is not a RefValue.");
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeap<Integer,Value> heap) throws ToyLanguageInterpreterException {
        RefValue eval = getValue(expression,symbolTable, heap);
        //System.out.println("evaluate: " + eval.toString());
        if(!(eval.getType() instanceof RefType))
            throw new ExpressionException(eval.toString() + " is not of RefType!");
        return heap.get(eval.getVal());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap{ " + expression.toString() + " }");
    }
}
