package Model.Statement;

import Collection.Dictionary.MyIDictionary;
import Collection.Stack.MyIStack;
import Collection.Stack.MyStack;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;

import java.io.IOException;

public class ForkStatement implements IStatement{
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
         statement.typeCheck(typeEnv.createDictionaryDuplicate());
         return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IOException, ToyLanguageInterpreterException {
        MyStack<IStatement> forkExeStack = new MyStack<>();
        //forkExeStack.push(statement);
        //state.setState(forkExeStack, state.getSymbolTable().createDictionaryDuplicate(), state.getHeap(), state.getOutputList(), statement.cloneStatement(), state.getFileTable());
        return new ProgramState(forkExeStack, state.getSymbolTable().createDictionaryDuplicate(), state.getHeap(), state.getSemaphoreTable(),state.getOutputList(), statement.cloneStatement(), state.getFileTable());
        //return state;
    }

    @Override
    public String toString() {
        return String.format("Fork{" + statement.toString() + "\n");
    }

    @Override
    public IStatement cloneStatement() {
        return new ForkStatement(this.statement);
    }
}
