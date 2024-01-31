package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Model.Type.Type;
import Model.Exceptions.ToyLanguageInterpreterException;
import javafx.util.Pair;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Collection.SemaphoreTable.MyISemaphoreTable;
import Model.Value.IntValue;
import Model.Value.Value;

import java.io.IOException;
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
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if (symTable.containsKey(var)) {
            if (symTable.get(var).getType().isEqualWith(new IntType()).getVal()) {
                IntValue fi = (IntValue) symTable.get(var);
                int foundIndex = fi.getVal();
                if (semaphoreTable.getSemaphoreTable().containsKey(foundIndex)) {
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
                    List<Integer> threads = foundSemaphore.getValue();
                    Integer nMax = foundSemaphore.getKey();
                    if (threads.contains(state.getId()))
                        threads.remove((Integer) state.getId());
                    semaphoreTable.update(foundIndex, new Pair<>(foundSemaphore.getKey(), foundSemaphore.getValue()));
                } else {
                    throw new ToyLanguageInterpreterException("Index not in the semaphore table!");
                }
            } else {
                throw new ToyLanguageInterpreterException("Index must be of int type!");
            }
        } else {
            throw new ToyLanguageInterpreterException("Index not in symbol table!");
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
        if (typeEnv.get(var).isEqualWith(new IntType()).getVal()) {
            return typeEnv;
        } else {
            throw new ToyLanguageInterpreterException(String.format("%s is not int!", var));
        }
    }

    @Override
    public String toString() {
        return String.format("release(%s)", var);
    }
}