package Model.Statement;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;

public class ReturnStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        state.getSymbolTable().pop();
        return null;
    }

    @Override
    public IStatement cloneStatement() {
        return new ReturnStatement();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}