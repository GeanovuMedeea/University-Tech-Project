package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyHeap;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Model.Exceptions.ExpressionException;
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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        Type typevar = typeEnv.get(name);
        Type typexp = expression.typeCheck(typeEnv);
        if(typevar.isEqualWith(typexp).getVal())
            return typeEnv;
        else throw new ExpressionException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIStack<MyIDictionary<String,Value>>  stackSymbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> symbolTable = stackSymbolTable.peek();

        Value value;
        try{
            MyIHeap<Integer,Value> heap = state.getHeap();
            value = expression.evaluate(symbolTable,heap);
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
        state.setSymbolTable(stackSymbolTable);
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