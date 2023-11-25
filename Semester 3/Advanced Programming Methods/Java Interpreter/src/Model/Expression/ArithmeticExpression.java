package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.*;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Type.IntType;
import Model.Value.StringValue;
import Model.Value.Value;

public class ArithmeticExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private OperationSelection operation;

    public ArithmeticExpression(Expression exp1, Expression exp2, OperationSelection operation){
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operation = operation;
    }

    private IntValue getValue(Expression expression, MyIDictionary<String, Value> symTable) throws ExpressionException, ToyLanguageInterpreterException {
        Value value = expression.evaluate(symTable);
        if (value instanceof IntValue)
            return (IntValue) value;
        throw new ExpressionException(value.toString() + " is not an IntValue!");
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable) throws ToyLanguageInterpreterException {
        IntValue eval1 = getValue(expression1,symbolTable);
        IntValue eval2 = getValue(expression2,symbolTable);
        if(!(eval1.getType() instanceof  IntType))
            throw new ExpressionException(eval1.toString() + " is not of IntType!");
        if(!(eval2.getType() instanceof  IntType))
            throw new ExpressionException(eval2.toString() + " is not of IntType!");
        if(operation == OperationSelection.PLUS) return new IntValue(eval1.getVal() + eval2.getVal());
        else if(operation == OperationSelection.MINUS) return new IntValue(eval1.getVal() - eval2.getVal());
        else if(operation == OperationSelection.MULTIPLY) return new IntValue(eval1.getVal() * eval2.getVal());
        else if(operation == OperationSelection.DIVIDE){
            if(eval2.getVal() == 0) throw new DivisionByZero("Error trying to divide by zero.");
            return new IntValue(eval1.getVal() / eval2.getVal());
        }
        return new IntValue(-1);
    }

    @Override
    public String toString(){
        StringValue result = new StringValue("( " + expression1.toString());
        if(operation == OperationSelection.PLUS) result = new StringValue(result.getVal() +  " + ");
        else if(operation == OperationSelection.MINUS) result = new StringValue(result.getVal() +  " - ");
        else if(operation == OperationSelection.MULTIPLY) result = new StringValue(result.getVal() +  " * ");
        else if(operation == OperationSelection.DIVIDE) result = new StringValue(result.getVal() +  " / ");
        result = new StringValue(result.getVal() +  expression2.toString() + " )");
        return result.getVal();
    }
}