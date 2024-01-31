package Collection.SemaphoreTable;

import Model.Exceptions.ToyLanguageInterpreterException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface MyISemaphoreTable {
    void put(int key, Pair<Integer, List<Integer>> value) throws ToyLanguageInterpreterException;
    Pair<Integer, List<Integer>> get(int key) throws ToyLanguageInterpreterException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Pair<Integer, List<Integer>> value) throws ToyLanguageInterpreterException;
    HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();
    void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> newSemaphoreTable);
    String toString();
}