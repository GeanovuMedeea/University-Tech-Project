package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Stack.MyIStack;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class AssignStatement implements IStatement {
    private String name;
    private Expression expression;

    public AssignStatement(String name, Expression expression){
        this.name = name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value value;
        try{
            value = expression.evaluate(symbolTable);
        }
        catch (ToyLanguageInterpreterException e){
            throw new ToyLanguageInterpreterException(e.getMessage());
        }
        if(symbolTable.containsKey(name)) {
            if (value.getType().isEqualWith(symbolTable.get(name).getType()).getVal())
                symbolTable.put(name, value);
            else throw new StatementExecutionException("The types do not match for the values in symtable!");
        }
        else throw new VariableNotFoundException("Variable doesn't exist!");
        return null;
    }
    public IStatement cloneStatement(){
        return new AssignStatement(name,expression);
    }

    @Override
    public String toString(){
        return name + " = " + expression.toString();
    }
}