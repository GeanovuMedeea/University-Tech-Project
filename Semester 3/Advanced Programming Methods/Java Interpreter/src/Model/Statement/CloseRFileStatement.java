package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyHeap;
import Collection.Heap.MyIHeap;
import Model.Exceptions.FileException;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    private Expression expression;
    public CloseRFileStatement(Expression expression){this.expression = expression;}

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        if(!expression.typeCheck(typeEnv).isEqualWith(new StringType()).getVal())
            throw new ToyLanguageInterpreterException("CloseReadFile requires a string expression");
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws FileException, StatementExecutionException, ToyLanguageInterpreterException {
        MyIHeap<Integer,Value> heap = state.getHeap();
        Value value = expression.evaluate(state.getSymbolTable(),heap);
        if (!value.getType().isEqualWith(new StringType()).getVal())
            throw new StatementExecutionException(expression.toString() + " is not a StringValue");

        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.containsKey(fileName))
            throw new VariableNotFoundException(value.toString() + " doesn't exist in the symbolTable");

        BufferedReader bufferedReader = fileTable.get(fileName);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new FileException("Error closing the file  " + value.toString());
        }
        fileTable.removeFromDictionaryByKey(fileName);
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new CloseRFileStatement(expression);
    }
    @Override
    public String toString(){
        return "Close file: " + expression.toString();
    }
}