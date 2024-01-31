package Collection.Heap;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Exceptions.VariableNotFoundException;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.*;

public class MyHeap<K,V> implements MyIHeap<K,V>{
    private HashMap<K, V> hashMap;
    private Integer freeLocation = 1;
    public Integer newFreeLocation(){
        Random rand = new Random();
        freeLocation = rand.nextInt(100);
        while(freeLocation <= 0 || hashMap.containsKey(freeLocation))
            freeLocation=rand.nextInt(100);
        return freeLocation;
    }
    public MyHeap(){
        hashMap = new HashMap<K,V>();
        freeLocation = newFreeLocation();
    }
    public MyHeap(HashMap<K, V> mapToSet){
        hashMap = mapToSet;
        freeLocation = newFreeLocation();
    }
    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public HashMap<K,V> getContent() {
        return hashMap;
    }
    public Set<K> keySet() {
        return hashMap.keySet();
    }
    @Override
    public void setContent(Map<K,V> newMap) {
        hashMap.clear();
        for (K i : newMap.keySet()) {
            hashMap.put(i, newMap.get(i));
        }

    }
    @Override
    public V put(K key, V value){
        return hashMap.put(key, value);
    }
    @Override
    public K add(V value) {
        hashMap.put((K) freeLocation, value);
        K toReturn = (K)freeLocation;
        freeLocation = newFreeLocation();
        return toReturn;
    }

    @Override
    public void update(K position, V value) throws ToyLanguageInterpreterException {
        if (!hashMap.containsKey(position))
            throw new VariableNotFoundException(position.toString() + " is not present in the heap!");
        hashMap.put(position, value);
    }

    @Override
    public V get(K position) throws ToyLanguageInterpreterException {
        if (!hashMap.containsKey(position))
            throw new VariableNotFoundException(position.toString() + " is not present in the heap!");
        return hashMap.get(position);
    }

    @Override
    public Iterable<Map.Entry<K, V>> getAll() {
        return hashMap.entrySet();
    }

    /*@Override
    public Iterable<Map.Entry<K, Integer>> getAll() {
        HashMap<K,IntValue> temp = new HashMap<>();
        hashMap.keySet().stream().toList().forEach(p->temp.put(p,(IntValue) hashMap.get(p)));
        HashMap<K,Integer> temp2 = new HashMap<>();
        temp.keySet().stream().toList().forEach(k->temp2.put(k,temp.get(k).getVal()));
        return temp2.entrySet();
    }*/

    @Override
    public MyIHeap createHeapDuplicate() {
        MyIHeap<K,V> newHeap = new MyHeap<>();
        for(K position: hashMap.keySet())
            newHeap.put(position,hashMap.get(position));
        return newHeap;
    }

    @Override
    public String toString(){
        String result = "";
        for(K position: this.keySet()){
            result = result + "Address: " + position.toString() + " Value: " + hashMap.get(position).toString() + "\n";
        }
        return result;
    }
}
