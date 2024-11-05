package pl.edu.agh.kis.pz1.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {
    private MyMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyMap<>();
    }

    @Test
    void testPutNewElement() {
        assertTrue(map.put("key1", 1)); // Adding a new element should return true
        assertEquals(Integer.valueOf(1), map.get("key1")); // Verify the element was added correctly
    }

    @Test
    void testPutExistingElement() {
        map.put("key1", 1);
        assertFalse(map.put("key1", 2)); // Updating an existing key should return false
        assertEquals(Integer.valueOf(2), map.get("key1")); // Value should be updated to the new one
    }

    @Test
    void testRemoveExistingElement() {
        map.put("key1", 1);
        assertTrue(map.remove("key1")); // Removing an existing key should return true
        assertNull(map.get("key1")); // Verify that the element has been removed
    }

    @Test
    void testRemoveNonExistingElement() {
        assertFalse(map.remove("nonExistingKey")); // Removing a non-existing key should return false
    }

    @Test
    void testGetExistingElement() {
        map.put("key1", 1);
        assertEquals(Integer.valueOf(1), map.get("key1")); // Should return the correct value
    }

    @Test
    void testGetNonExistingElement() {
        assertNull(map.get("nonExistingKey")); // Should return null for a non-existing key
    }

    @Test
    void testContainsExistingKey() {
        map.put("key1", 1);
        assertTrue(map.contains("key1")); // Should return true for an existing key
    }

    @Test
    void testContainsNonExistingKey() {
        assertFalse(map.contains("nonExistingKey")); // Should return false for a non-existing key
    }

    @Test
    void testKeys() {
        map.put("key1", 1);
        map.put("key2", 2);
        List<String> keys = map.keys();

        assertEquals(2, keys.size()); // Should contain exactly 2 keys
        assertTrue(keys.contains("key1") && keys.contains("key2")); // Should contain the correct keys
    }

    @Test
    void testPutNullKey() {
        assertTrue(map.put(null, 1)); // Adding a null key should work
        assertEquals(Integer.valueOf(1), map.get(null)); // Should return the value for the null key
    }

    @Test
    void testRemoveNullKey() {
        map.put(null, 1);
        assertTrue(map.remove(null)); // Should remove the null key
        assertNull(map.get(null)); // Should return null as the null key is removed
    }
}

