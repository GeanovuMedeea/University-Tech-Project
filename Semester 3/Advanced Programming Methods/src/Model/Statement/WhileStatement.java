package Model.Statement;

import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement {
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        Value value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (!value.getType().isEqualWith(new BoolType()).getVal())
            throw new StatementExecutionException(value + " is not of BoolType.");
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("While( " + expression + " ){ " + statement + " }");
    }

    @Override
    public IStatement cloneStatement() {
        return new WhileStatement(expression,statement);
    }
}