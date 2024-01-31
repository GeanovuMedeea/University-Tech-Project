package Model.Statement;

import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Type.RefType;
import Model.Value.RefValue;
import javafx.util.Pair;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.SemaphoreTable.MyISemaphoreTable;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStatement implements IStatement{
    private final String var;
    private final Expression expression;
    private static final Lock lock = new ReentrantLock();

    public CreateSemaphoreStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();

        Value nr1 = expression.evaluate(symTable, heap);
        Type temp = nr1.getType();
        while(temp instanceof RefType)
            temp = temp.getInner();
        if (!temp.isEqualWith(new IntType()).getVal())
            throw new StatementExecutionException(nr1.toString() + " is not of int type!");
        IntValue nr = (IntValue) nr1;
        int number = nr.getVal();
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Pair<>(number, new ArrayList<>()));
        if (symTable.containsKey(var) && symTable.get(var).getType().isEqualWith(new IntType()).getVal())
            symTable.put(var, new IntValue(freeAddress));
        else
            throw new ToyLanguageInterpreterException(String.format("Error for variable %s: not defined/does not have int type!", var));
        lock.unlock();
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new CreateSemaphoreStatement(var,expression);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        if (typeEnv.get(var).isEqualWith(new IntType()).getVal()) {
            if (expression.typeCheck(typeEnv).isEqualWith(new IntType()).getVal())
                return typeEnv;
            else
                throw new ToyLanguageInterpreterException("Expression is not of int type!");
        } else {
            throw new ToyLanguageInterpreterException(String.format("%s is not of type int!", var));
        }
    }

    @Override
    public String toString() {
        return String.format("createSemaphore(%s, %s)", var, expression);
    }
}