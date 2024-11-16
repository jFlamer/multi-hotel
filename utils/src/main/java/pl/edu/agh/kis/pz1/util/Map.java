package pl.edu.agh.kis.pz1.util;

import java.util.List;


/**
 * This interface defines the contract for a Map data structure.
 */
public interface Map<K, V> {
    /**
     * Adds an element to the map under the given key.
     * If the provided key already exists, the method should replace the existing value.
     *
     * @param key the key (not null)
     * @param value the value associated with the key (not null)
     * @return true if the element was successfully added, false otherwise
     */
    boolean put(K key, V value);

    /**
     * Removes the specified key and the value associated with it from the map.
     *
     * @param key the key to be removed
     * @return true if the key was successfully removed, false otherwise
     */
    boolean remove(K key);

    /**
     * Returns the value associated with the specified key, or null if the key does not exist in the map.
     *
     * @param key the key (not null)
     * @return the value associated with the key, or null if the key does not exist
     */
    V get(K key);

    /**
     * Returns a list of all the keys in the map.
     *
     * @return java.util.List a list of keys
     */
    List<K> keys();

    /**
     * Checks whether the specified key exists in the map.
     *
     * @param key the key to check
     * @return true if the key exists in the map, false otherwise
     */
    boolean contains(K key);
}
