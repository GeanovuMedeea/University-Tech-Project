package Model.Statement;

import Model.ProgramState;
import Collection.Dictionary.MyIDictionary;
import Model.Type.Type;

import java.sql.Statement;

public class NopStatement implements IStatement {
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