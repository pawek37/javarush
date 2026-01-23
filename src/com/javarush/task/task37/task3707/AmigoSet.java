package com.javarush.task.task37.task3707;



import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
        addAll(collection);
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }
    @Override
    public Object clone() {
        try {
            AmigoSet<E> newSet = (AmigoSet<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (Exception e) {
            throw new InternalError();
        }
    }
    private void writeObject(ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(HashMapReflectionHelper.callHiddenMethod(map,"capacity"));
        s.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));

        // Write out size
        s.writeInt(HashMapReflectionHelper.callHiddenMethod(map,"size"));

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }
    private void readObject(ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        int capacity = s.readInt();
       float loadFacot = s.readFloat();
       map = new HashMap<>(capacity,loadFacot);
        int size = s.readInt();
        for (int i=0; i<size; i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);

        }
    }
}
