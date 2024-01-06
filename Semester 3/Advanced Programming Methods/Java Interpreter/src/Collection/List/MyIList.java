package Collection.List;

import Model.Value.BoolValue;
import Model.Value.IntValue;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface MyIList<T> {
    IntValue getListSize();
    BoolValue isEmpty();
    void add(T e);
    void clear();
    T get(int index);
    void forEach(Consumer<T> action); // Added forEach method

    void addAll(List<T> listToAdd);
    Stream<T> stream(); // Added stream method
    String toString();
}
