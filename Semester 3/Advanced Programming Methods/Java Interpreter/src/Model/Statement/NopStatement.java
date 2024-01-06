package Model.Statement;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Collection.Dictionary.MyIDictionary;
import Model.Type.Type;

import java.sql.Statement;

public class NopStatement implements IStatement {
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }
    public IStatement cloneStatement(){
        return new NopStatement();
    }
    @Override
    public String toString() {
        return "NopStatement";
    }
}