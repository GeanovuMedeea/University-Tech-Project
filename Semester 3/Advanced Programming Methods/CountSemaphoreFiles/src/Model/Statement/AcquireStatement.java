package Model.Statement;

import Collection.Stack.MyIStack;
import Model.Exceptions.ToyLanguageInterpreterException;
import javafx.util.Pair;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Collection.SemaphoreTable.MyISemaphoreTable;
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
        try{
            MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
            MyIStack<IStatement> executionStack = state.getExecutionStack();

            IntValue t = (IntValue) state.getSymbolTable().get(var);
            int foundIndex = t.getVal();

            if(!state.getSymbolTable().containsKey(var))
                throw new Exception("No such variable in symbolTable");
            Pair<Integer, List<Integer>> semaphoreValue = semaphoreTable.get(foundIndex);
            List<Integer> threads = semaphoreValue.getValue();
            Integer nMax = semaphoreValue.getKey();
            if(nMax != threads.size())
            {
                if(threads.contains(state.getId()))
                    throw new Exception("Already in process");
                threads.add(state.getId());
                state.getSemaphoreTable().update(foundIndex, new Pair<>(nMax, threads));
            }else
            {
                executionStack.push(this);
                //state.setExecutionStack(executionStack);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }


/*        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if (symTable.containsKey(var)) {
            if (symTable.get(var).getType().isEqualWith(new IntType()).getVal()){
                IntValue fi = (IntValue) symTable.get(var);
                int foundIndex = fi.getVal();
                if (semaphoreTable.getSemaphoreTable().containsKey(foundIndex)) {
                    Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
                    int NL = foundSemaphore.getValue().size();
                    int N1 = foundSemaphore.getKey();
                    if (N1 == NL) {
                        if (!foundSemaphore.getValue().contains(state.getId())) {
                            foundSemaphore.getValue().add(state.getId());
                            semaphoreTable.update(foundIndex, new Pair<>(N1, foundSemaphore.getValue()));
                        }
                    } else {
                        state.getExecutionStack().push(this);
                    }
                } else {
                    throw new ToyLanguageInterpreterException("Index not a key in the semaphore table!");
                }
            } else {
                throw new ToyLanguageInterpreterException("Index must be of int type!");
            }
        } else {
            throw new ToyLanguageInterpreterException("Index not in symbol table!");
        }
        lock.unlock();
        return null;
    }*/

    @Override
    public IStatement cloneStatement() {
        return new AcquireStatement(var);
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
        return String.format("acquire(%s)", var);
    }
}