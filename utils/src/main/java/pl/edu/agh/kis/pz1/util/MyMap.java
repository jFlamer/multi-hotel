package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The MyMap class is a custom implementation of the Map interface.
 * It stores key-value pairs using two ArrayLists: one for keys and one for values.
 * It provides basic functionality for inserting, removing, and retrieving key-value pairs.
 *
 * Key Features:
 * - **Put**: Adds a new key-value pair to the map or updates the value if the key already exists.
 * - **Remove**: Removes a key-value pair from the map based on the provided key.
 * - **Get**: Retrieves the value associated with a given key.
 * - **Contains**: Checks whether a specific key exists in the map.
 * - **Keys**: Returns a list of all keys stored in the map.
 *
 * This implementation is based on two parallel ArrayLists: one to store the keys and another to store the corresponding values.
 * This means the position of a key in the `keys` list corresponds to the position of its value in the `values` list.
 *
 * This implementation is simple and may not be as efficient as more optimized data structures like hash maps, but it demonstrates the
 * fundamental concept of a key-value store.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class MyMap<K, V> implements Map<V, K> {
    private ArrayList<K> keys;
    private ArrayList<V> values;

    /**
     * Constructs a new empty map.
     */
    public MyMap() {
        this.keys = new ArrayList<K>();
        this.values = new ArrayList<V>();
    }

    /**
     * Adds a key-value pair to the map. If the key already exists, the value is updated.
     *
     * @param key the key to associate with the value
     * @param value the value to associate with the key
     * @return true if the key-value pair was added, false if the value was updated
     */
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

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key the key to remove
     * @return true if the key was found and removed, false if the key was not found
     */
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

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or null if the key is not found
     */
    @Override
    public V get(Object key) {
        int idx = keys.indexOf((K)key);
        if (idx == -1) {
            return null;
        } else {
            return values.get(idx);
        }
    }

    /**
     * Returns a list of all the keys in the map.
     *
     * @return a list of all keys
     */
    @Override
    public List keys() {
        return keys;
    }

    /**
     * Checks if the specified key exists in the map.
     *
     * @param key the key to check
     * @return true if the key is present in the map, false otherwise
     */
    @Override
    public boolean contains(Object key) {
        return keys.contains(key);
    }
}
