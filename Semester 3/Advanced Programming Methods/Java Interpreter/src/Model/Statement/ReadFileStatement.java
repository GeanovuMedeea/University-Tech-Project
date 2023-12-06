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
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private Expression expression;
    private StringValue varName;
    public ReadFileStatement(Expression expression,String varName){
        this.expression = expression;
        this.varName = new StringValue(varName);}

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException,VariableNotFoundException,StatementExecutionException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symbolTable.containsKey(varName.getVal()))
            throw new VariableNotFoundException(varName + " doesn't exist in the symbolTable");

        Value value = symbolTable.get(varName.getVal());
        if (!value.getType().isEqualWith(new IntType()).getVal())
            throw new StatementExecutionException(value.toString() + " is not of type IntType");

        MyIHeap<Integer,Value> heap = state.getHeap();
        value = expression.evaluate(symbolTable,heap);
        if (!value.getType().isEqualWith(new StringType()).getVal())
            throw new StatementExecutionException(value.toString() + " name is not of type StringType");

        StringValue fileNameValue = (StringValue) value;
        if (!fileTable.containsKey(fileNameValue))
            throw new VariableNotFoundException("fileTable doesn't contain " + fileNameValue);

        BufferedReader bufferedReader = fileTable.get(fileNameValue);
        try {
            String line = bufferedReader.readLine();
            if (line != null)
                symbolTable.put(varName.getVal(), new IntValue(Integer.valueOf(line)));
            else
                symbolTable.put(varName.getVal(), new IntValue(0));
        } catch (IOException e) {
            throw new FileException("Can't read from the file " + fileNameValue);
        }
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new ReadFileStatement(expression,varName.getVal());
    }
    @Override
    public String toString(){
        return "Read from file: " + expression.toString() + " " + varName;
    }
}
