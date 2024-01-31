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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStatement{
    private final String var;
    private final Expression expression1;
    private final Expression expression2;
    private static final Lock lock = new ReentrantLock();

    public NewSemaphoreStatement(String var, Expression expression1, Expression expression2) {
        this.var = var;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap heap = state.getHeap();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphoreTable();
        IntValue nr1 = (IntValue) (expression1.evaluate(symTable, heap));
        IntValue nr2 = (IntValue) (expression2.evaluate(symTable, heap));
        int number1 = nr1.getVal();
        int number2 = nr2.getVal();
        int freeAddress = semaphoreTable.getFreeAddress();
        semaphoreTable.put(freeAddress, new Tuple<>(number1, new ArrayList<>(), number2));
        if (symTable.containsKey(var) && symTable.get(var).getType().isEqualWith(new IntType()).getVal())
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new ToyLanguageInterpreterException(String.format("%s in not defined in the symbol table!", var));
        lock.unlock();
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new NewSemaphoreStatement(var, expression1, expression2);

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("newSemaphore(%s, %s, %s)", var, expression1, expression2);
    }
}