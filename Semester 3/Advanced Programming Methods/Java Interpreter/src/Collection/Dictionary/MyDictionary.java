package Collection.Dictionary;

import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<K,V>();
    }

    @Override
    public V get(K key){
        return dictionary.get(key);
    }

    @Override
    public K getKeyByValue(V value){
        for(K key : dictionary.keySet()){
            if(dictionary.get(key).equals(value))
                return key;
        }
        return null;
    }

    @Override
    public V put(K key, V value){
        return dictionary.put(key, value);
    }

    @Override
    public String toString(){
        String result = "";
        for(K key: this.keySet()){
            result = result + "Variable name: " + key.toString() + " Value: " + dictionary.get(key).toString() + "\n";
        }

        return result;
        //return dictionary.toString();
    }

    @Override
    public int sizeOfDictionary() {
        return dictionary.size();
    }

    @Override
    public boolean containsKey(K name) {
        return dictionary.containsKey(name);
    }

    @Override
    public void removeFromDictionaryByKey(K id){
        dictionary.remove(id);
    }
    @Override
    public Collection<V> getValues() {
        return dictionary.values();
    }
    @Override
    public boolean containsValue(V element) {
        return dictionary.containsValue(element);
    }
    @Override
    public Set<K> keySet() {
        return dictionary.keySet();
    }

    @Override
    public MyIDictionary<K, V> createDictionaryDuplicate() {
        MyIDictionary<K, V> dictionaryTemporary = new MyDictionary<>();
        for(K key : this.keySet())
            dictionaryTemporary.put(key, dictionary.get(key));
        return dictionaryTemporary;
    }
}