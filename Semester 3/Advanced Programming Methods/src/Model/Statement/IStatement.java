package Model.Statement;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Model.Type.Type;
import java.io.FileNotFoundException;
import java.io.IOException;

import Collection.Dictionary.MyIDictionary;

public interface IStatement {
    ProgramState execute(ProgramState state) throws IOException, ToyLanguageInterpreterException;
    IStatement cloneStatement();
    String toString();
}