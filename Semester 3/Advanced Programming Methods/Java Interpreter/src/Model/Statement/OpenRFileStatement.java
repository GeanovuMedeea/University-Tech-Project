package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyHeap;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    private Expression expression;
    public OpenRFileStatement(Expression expression){this.expression = expression;}
    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        Value value;
        try {
            MyIHeap<Integer,Value> heap = state.getHeap();
            value = expression.evaluate(state.getSymbolTable(),heap);
        } catch (ToyLanguageInterpreterException e) {
            throw new ToyLanguageInterpreterException(e.getMessage());
        }
        if (!value.getType().isEqualWith(new StringType()).getVal())
            throw new StatementExecutionException("The type is not String!");
        else {
            StringValue fileName = (StringValue) value;
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.containsKey(fileName)) {
                throw new StatementExecutionException("The file " + fileTable.toString() + " is opened.\n");
            }
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(fileName.getVal()));
            } catch (IOException e) {
                throw new StatementExecutionException("File " + fileTable.toString() + " can't open!");
            }
            fileTable.put(fileName, bufferedReader);
            return null;
        }
    };
    @Override
    public IStatement cloneStatement(){return new OpenRFileStatement(expression);}
    @Override
    public String toString(){
        return "Open file: " + expression.toString();
    };
}
