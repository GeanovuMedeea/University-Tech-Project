package Collection.ProcTable;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.ToyLanguageInterpreterException;
import javafx.util.Pair;
import Model.Statement.IStatement;

import java.util.*;

public interface MyIProcTable {
    boolean isDefined(String key);
    void put(String key, Pair<List<String>, IStatement> value);
    Pair<List<String>, IStatement> lookUp(String key) throws ToyLanguageInterpreterException;
    void update(String key,  Pair<List<String>, IStatement> value) throws ToyLanguageInterpreterException;
    Collection< Pair<List<String>, IStatement>> values();
    void remove(String key) throws ToyLanguageInterpreterException;
    Set<String> keySet();
    HashMap<String,  Pair<List<String>, IStatement>> getContent();
    MyIDictionary<String, Pair<List<String>, IStatement>> deepCopy() throws ToyLanguageInterpreterException;
    String toString();
}