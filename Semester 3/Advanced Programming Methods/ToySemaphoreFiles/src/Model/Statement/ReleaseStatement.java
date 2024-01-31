package Model.Statement;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.ToySemaphore.MyIToySemaphoreTable;
import Collection.Tuple;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ReleaseStatement implements IStatement{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public ReleaseStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphoreTable();
        if (symTable.containsKey(var)) {
            if (symTable.get(var).getType().isEqualWith(new IntType()).getVal()) {
                IntValue fi = (IntValue) symTable.get(var);
                int foundIndex = fi.getVal();
                if (semaphoreTable.containsKey(foundIndex)) {
                    Tuple<Integer, List<Integer>, Integer> foundSemaphore = semaphoreTable.get(foundIndex);
                    if (foundSemaphore.getSecond().contains(state.getId())) {
                        foundSemaphore.getSecond().remove((Integer) state.getId());
                    }
                    semaphoreTable.update(foundIndex, new Tuple<>(foundSemaphore.getFirst(), foundSemaphore.getSecond(), foundSemaphore.getThird()));
                } else {
                    throw new ToyLanguageInterpreterException("Index not found in the semaphore table!");
                }
            } else {
                throw new ToyLanguageInterpreterException("Index must be of int type!");
            }
        } else {
            throw new ToyLanguageInterpreterException("Index not found in the symbol table!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new ReleaseStatement(var);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("release(%s)", var);
    }
}