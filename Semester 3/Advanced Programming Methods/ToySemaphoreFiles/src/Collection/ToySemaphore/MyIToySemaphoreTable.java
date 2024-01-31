package Collection.ToySemaphore;

import Collection.Tuple;
import Model.Exceptions.ToyLanguageInterpreterException;

import java.util.HashMap;
import java.util.List;

public interface MyIToySemaphoreTable {
    void put(int key, Tuple<Integer, List<Integer>, Integer> value) throws ToyLanguageInterpreterException;
    Tuple<Integer, List<Integer>, Integer> get(int key) throws ToyLanguageInterpreterException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Tuple<Integer, List<Integer>, Integer> value) throws ToyLanguageInterpreterException;
    HashMap<Integer, Tuple<Integer, List<Integer>, Integer>> getToySemaphoreTable();
    void setToySemaphoreTable(HashMap<Integer, Tuple<Integer, List<Integer>, Integer>> newToySemaphoreTable);
    String toString();
}