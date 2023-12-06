package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStatement implements IStatement{
    private final String varName;
    private final Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer,Value> heap = state.getHeap();
        if (!symTable.containsKey(varName))
            throw new VariableNotFoundException(varName + "is not present in the symbolTable.");
        Value varValue = symTable.get(varName);
        System.out.println("var: " + varValue.toString());
        if (!(varValue instanceof RefValue))
            throw new StatementExecutionException(varValue.toString() + " is not of ReferenceType.");
        Value evaluated = expression.evaluate(symTable, heap);
        System.out.println("eval: " + evaluated.toString());
        System.out.println("WRITE!");
        Type temp = varValue.getType();
        while(temp instanceof RefType)
            temp = temp.getInner();
        if (!evaluated.getType().isEqualWith(temp).getVal())
            throw new StatementExecutionException(evaluated.toString() + " is not of "+ varValue.getType().toString());
        heap.update(((RefValue) varValue).getVal(), evaluated);
        return null;
    }

    @Override
    public String toString() {
        return String.format("WriteHeap{ " +varName.toString()+ " " + expression.toString()+" }");
    }

    @Override
    public IStatement cloneStatement() {
        return new WriteHeapStatement(varName,expression);
    }
}
