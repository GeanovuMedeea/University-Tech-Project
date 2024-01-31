package Model.Statement;

import Model.Exceptions.ToyLanguageInterpreterException;

import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Collection.ToySemaphore.MyIToySemaphoreTable;
import Collection.Tuple;
import Model.Value.IntValue;
import Model.Value.Value;


import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public AcquireStatement(String var) {
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
                    int NL = foundSemaphore.getSecond().size();
                    int N1 = foundSemaphore.getFirst();
                    int N2 = foundSemaphore.getThird();
                    if ((N1 - N2) > NL) {
                        if (!foundSemaphore.getSecond().contains(state.getId())) {
                            foundSemaphore.getSecond().add(state.getId());
                            semaphoreTable.update(foundIndex, new Tuple<>(N1, foundSemaphore.getSecond(), N2));
                        }
                    } else {
                        state.getExecutionStack().push(this);
                    }
                } else {
                    throw new ToyLanguageInterpreterException("Index is not in the semaphore table!");
                }
            } else {
                throw new ToyLanguageInterpreterException("Index does not have the int type!");
            }
        } else
            throw new ToyLanguageInterpreterException("Index not in the symbol table!");
        lock.unlock();
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new AcquireStatement(var);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", var);
    }
}