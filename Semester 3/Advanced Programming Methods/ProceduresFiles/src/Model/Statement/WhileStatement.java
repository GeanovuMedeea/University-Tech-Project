package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class WhileStatement implements IStatement {
    private final Expression expression;
    private final IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        Type typexp = expression.typeCheck(typeEnv);
        if(typexp.isEqualWith(new BoolType()).getVal()){
            statement.typeCheck(typeEnv.createDictionaryDuplicate());
            return typeEnv;
        }
        else throw new StatementExecutionException("The condition of WHILE has not the type bool");
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIStack<MyIDictionary<String,Value>> stackSymbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> symbolTable = stackSymbolTable.peek();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value value = expression.evaluate(symbolTable, heap);
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