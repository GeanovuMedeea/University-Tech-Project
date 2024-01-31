package Collection.List;

import Model.Value.BoolValue;
import Model.Value.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }

    @Override
    public IntValue getListSize() {
        return new IntValue(list.size());
    }

    @Override
    public void add(T e) {list.add(e);}

    @Override
    public BoolValue isEmpty() {
        return new BoolValue(list.isEmpty());
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void forEach(Consumer<T> action) {
        list.forEach(action);
    }

    @Override
    public void addAll(List<T> listToAdd) {
        listToAdd.forEach((temp)->{add(temp);});
    }

    @Override
    public ArrayList<T> getAll() {
        return list;
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    @Override
    public String toString() {
        String finalList = "[";
        for(int i = 0; i< getListSize().getVal(); i++)
            if(i!= getListSize().getVal()-1)
                finalList = finalList + get(i).toString() + ", ";
            else
                finalList = finalList + get(i).toString();
        //return list.toString();
        finalList = finalList + "]";
        return finalList;
    }
}