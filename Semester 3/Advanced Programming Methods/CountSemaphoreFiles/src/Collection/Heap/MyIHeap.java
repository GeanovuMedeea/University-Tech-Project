package Collection.Heap;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<K,V> {
    Integer getFreeLocation();
    Map<K, V> getContent();
    V put(K key, V value);
    void setContent(Map<K, V> newMap);
    K add(V value);
    Set<K> keySet();

    void update(K position, V value) throws ToyLanguageInterpreterException;

    V get(K position) throws ToyLanguageInterpreterException;

    Iterable<Map.Entry<K, V>> getAll();

    MyIHeap<K, V> createHeapDuplicate();
    String toString();
}
