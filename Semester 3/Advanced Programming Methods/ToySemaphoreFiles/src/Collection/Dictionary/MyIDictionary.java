package Collection.Dictionary;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    V get(K key);
    V put(K key, V value);
    String toString();
    int sizeOfDictionary();
    boolean containsKey(K name);
    void removeFromDictionaryByKey(K id);
    Collection<V> getValues();
    boolean containsValue(V element);
    Set<K> keySet();
    K getKeyByValue(V value);
    void update(K key,V value);
    MyIDictionary<K, V> createDictionaryDuplicate();
    Iterable<Map.Entry<K, V>> getAll();
}