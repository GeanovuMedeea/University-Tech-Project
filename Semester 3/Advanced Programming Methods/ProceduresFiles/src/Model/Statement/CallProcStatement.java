package Model.Statement;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.ProcTable.MyIProcTable;
import Model.Value.Value;

import java.util.List;

public class CallProcStatement implements IStatement{
    private final String procedureName;
    private final List<Expression> expressions;

    public CallProcStatement(String procedureName, List<Expression> expressions) {
        this.procedureName = procedureName;
        this.expressions = expressions;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIDictionary<String, Value> symTable = state.getTopSymTable();
        MyIHeap heap = state.getHeap();
        MyIProcTable procTable = state.getProcTable();
        if (procTable.isDefined(procedureName)) {
            List<String> variables = procTable.lookUp(procedureName).getKey();
            IStatement statement = procTable.lookUp(procedureName).getValue();
            MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
            for(String var: variables) {
                int ind = variables.indexOf(var);
                newSymTable.put(var, expressions.get(ind).evaluate(symTable, heap));
            }
            state.getSymbolTable().push(newSymTable);
            state.getExecutionStack().push(new ReturnStatement());
            state.getExecutionStack().push(statement);
        } else {
            throw new ToyLanguageInterpreterException("Procedure not defined!");
        }
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new CallProcStatement(procedureName, expressions);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("call %s%s", procedureName, expressions.toString());
    }
}