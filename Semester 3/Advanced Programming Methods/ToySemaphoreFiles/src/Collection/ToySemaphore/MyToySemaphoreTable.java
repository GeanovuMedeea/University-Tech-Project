package Collection.ToySemaphore;

import Collection.Tuple;
import Model.Exceptions.ToyLanguageInterpreterException;

import java.util.HashMap;
import java.util.List;

public class MyToySemaphoreTable implements MyIToySemaphoreTable{
    private HashMap<Integer, Tuple<Integer, List<Integer>, Integer>> toySemaphoreTable;
    private int freeLocation = 0;

    public MyToySemaphoreTable() {
        this.toySemaphoreTable = new HashMap<>();
    }

    @Override
    public void put(int key, Tuple<Integer, List<Integer>, Integer> value) throws ToyLanguageInterpreterException {
        synchronized (this) {
            if (!toySemaphoreTable.containsKey(key)) {
                toySemaphoreTable.put(key, value);
            } else {
                throw new ToyLanguageInterpreterException(String.format("Toy semaphore table already contains the key %d!", key));
            }
        }
    }

    @Override
    public Tuple<Integer, List<Integer>, Integer> get(int key) throws ToyLanguageInterpreterException {
        synchronized (this) {
            if (toySemaphoreTable.containsKey(key)) {
                return toySemaphoreTable.get(key);
            } else {
                throw new ToyLanguageInterpreterException(String.format("Toy semaphore table doesn't contain the key %d!", key));
            }
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return toySemaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            freeLocation++;
            return freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocation = freeAddress;
        }
    }

    @Override
    public void update(int key, Tuple<Integer, List<Integer>, Integer> value) throws ToyLanguageInterpreterException {
        synchronized (this) {
            if (toySemaphoreTable.containsKey(key)) {
                toySemaphoreTable.replace(key, value);
            } else {
                throw new ToyLanguageInterpreterException(String.format("Toy semaphore table doesn't contain key %d!", key));
            }
        }
    }

    @Override
    public HashMap<Integer, Tuple<Integer, List<Integer>, Integer>> getToySemaphoreTable() {
        synchronized (this) {
            return toySemaphoreTable;
        }
    }

    @Override
    public void setToySemaphoreTable(HashMap<Integer, Tuple<Integer, List<Integer>, Integer>> newToySemaphoreTable) {
        synchronized (this) {
            this.toySemaphoreTable = newToySemaphoreTable;
        }
    }

    @Override
    public String toString() {
        StringBuilder toySemaphoreTableStringBuilder = new StringBuilder();
        for (int key: toySemaphoreTable.keySet()) {
            toySemaphoreTableStringBuilder.append(String.format("%d -> (%d, %s, %d)\n", key, toySemaphoreTable.get(key).getFirst(), toySemaphoreTable.get(key).getSecond(), toySemaphoreTable.get(key).getThird()));
        }
        return toySemaphoreTableStringBuilder.toString();
}
}