package Model.Statement;
import Collection.Stack.MyIStack;
import Model.Exceptions.ComparisonException;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class IfStatement implements IStatement{
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException, StatementExecutionException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        Value value = new IntValue(0);
        try{
            value = expression.evaluate(state.getSymbolTable());
        }
        catch (ToyLanguageInterpreterException e){
            throw new ToyLanguageInterpreterException(e.getMessage());
        }
        if(!value.getType().isEqualWith(new BoolType()).getVal())
            throw new StatementExecutionException("The type of the value is not Boolean!");
        else {
            if (value.equals(new BoolValue(true))) stack.push(thenStatement);
            else stack.push(elseStatement);
            return null;
        }
    }
    public IStatement cloneStatement(){
        return new IfStatement(expression,thenStatement,elseStatement);
    }
    @Override
    public String toString(){
        return "if(" + expression.toString() + ") then " + thenStatement.toString() + " else " + elseStatement.toString();
    }
}