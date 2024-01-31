package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

import java.io.IOException;

public class NewStatement implements IStatement{
    private final String variableName;
    private final Expression expression;

    public NewStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        Type typevar = typeEnv.get(variableName);
        Type typexp = expression.typeCheck(typeEnv);
        Type temp;
        temp = new RefType(typevar.getInner());
        while(temp.getInner() instanceof RefType) {
            temp = temp.getInner();
        }
        if(temp instanceof RefType)
            temp = temp.getInner();
        if(typexp.isEqualWith(temp).getVal())
            return typeEnv;
        else throw new ExpressionException("NEW stmt: right hand side and left hand side are different types");
    }


    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException{
        MyIStack<MyIDictionary<String,Value>> stackSymbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> symbolTable = stackSymbolTable.peek();
        MyIHeap<Integer,Value> heap = state.getHeap();
        if (!symbolTable.containsKey(variableName))
            throw new VariableNotFoundException(variableName + " is not in symTable.");
        Value varValue = symbolTable.get(variableName);
        if (!(varValue.getType() instanceof RefType))
            throw new StatementExecutionException(variableName + " is not of ReferenceType.");
        Value evaluated = expression.evaluate(symbolTable, heap);
        Type locationType = varValue.getType();
        while((locationType.getInner() instanceof RefType))
            locationType = locationType.getInner();
        //System.out.println(locationType.getInner().toString() + " " + evaluated.getType().toString()+"\n");
        if (!locationType.getInner().isEqualWith(evaluated.getType()).getVal())
            throw new StatementExecutionException(variableName + " is not of " + evaluated.getType().toString());
        Integer newPosition = heap.add(evaluated);
        symbolTable.put(variableName, new RefValue(newPosition, varValue.getType()));
        state.setSymbolTable(stackSymbolTable);
        return null;
    }

    @Override
    public String toString() {
        return String.format("New{ "+ variableName + ", " + expression + " }");
    }

    @Override
    public IStatement cloneStatement() {
        return new NewStatement(variableName,expression);
    }

}
