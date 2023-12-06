package Collection.List;

import Model.Value.BoolValue;
import Model.Value.IntValue;

public interface MyIList<T> {
    IntValue getListSize();
    BoolValue isEmpty();
    void add(T e);
    void clear();
    T get(int index);
    String toString();
}
