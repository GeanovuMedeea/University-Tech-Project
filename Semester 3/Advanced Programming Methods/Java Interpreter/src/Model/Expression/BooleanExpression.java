package Model.Expression;
import Collection.Heap.MyIHeap;
import Model.Exceptions.*;
import Collection.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class BooleanExpression implements Expression {
    private Expression expressionLeft;
    private Expression expressionRight;
    private String comparisonOperator;

    public BooleanExpression(Expression expression_left, Expression expression_right, String comparisonOperator){
        this.comparisonOperator = comparisonOperator;
        this.expressionLeft = expression_left;
        this.expressionRight = expression_right;
    }

    @Override
    public String toString() {
        return "( " + expressionLeft.toString() + " " + comparisonOperator + " " + expressionRight.toString() + " )";
    }
    private IntValue getValue(Expression expression, MyIDictionary<String, Value> symTable, MyIHeap<Integer,Value> heap) throws ExpressionException, ToyLanguageInterpreterException {
        Value value = expression.evaluate(symTable,heap);
        if (value instanceof IntValue)
            return (IntValue) value;
        throw new ExpressionException(value.toString() + " is not a BoolValue");
    }
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable,MyIHeap<Integer,Value> heap) throws ToyLanguageInterpreterException {
        IntValue expr1 = getValue(expressionLeft,symbolTable,heap);
        IntValue expr2 = getValue(expressionRight,symbolTable,heap);
        boolean booleanEvaluationResult;

        switch (comparisonOperator){
            case "<" :
                booleanEvaluationResult = expr1.getVal() < expr2.getVal();
                break;
            case "<=" :
                booleanEvaluationResult = expr1.getVal() <= expr2.getVal();
                break;
            case ">" :
                booleanEvaluationResult = expr1.getVal() > expr2.getVal();
                break;
            case ">=" :
                booleanEvaluationResult = expr1.getVal() >= expr2.getVal();
                break;
            case "==" :
                booleanEvaluationResult = expr1.equals(expr2);
                break;
            case "!=" :
                booleanEvaluationResult = !expr1.equals(expr2);
                break;
            default:
                throw new ComparisonException(comparisonOperator + " is not a valid comparison operator.");
        }
        return booleanEvaluationResult ? new BoolValue(true) : new BoolValue(false);
    }
}