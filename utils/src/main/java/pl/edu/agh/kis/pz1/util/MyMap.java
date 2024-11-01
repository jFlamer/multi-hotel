package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.List;


public class MyMap<K, V> implements Map {
    private ArrayList<K> keys;
    private ArrayList<V> values;

    public MyMap() {
        this.keys = new ArrayList<K>();
        this.values = new ArrayList<V>();
    }
    @Override
    public boolean put(Object key, Object value) {
        int idxOfKey = keys.indexOf((K)key);
        if (idxOfKey == -1) {
            keys.add((K)key);
            values.add((V)value);
            return true;
        } else {
            values.set(idxOfKey, (V)value);
            return false;
        }
    }

    @Override
    public boolean remove(Object key) {
        int idx = keys.indexOf((K)key);
        if (idx == -1) {
            return false;
        } else {
            keys.remove(idx);
            values.remove(idx);
            return true;
        }
    }

    @Override
    public V get(Object key) {
        int idx = keys.indexOf((K)key);
        if (idx == -1) {
            return null;
        } else {
            return values.get(idx);
        }
    }

    @Override
    public List keys() {
        return keys;
    }

    @Override
    public boolean contains(Object key) {
        return keys.contains(key);
    }
}
