package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Import JUnit 5 assertions
import pl.edu.agh.kis.pz1.main.model.Guest;

/**
 * Unit tests for the Guest class.
 */
public class GuestTest {

    private Guest guest;

    @BeforeEach
    public void setUp() {
        guest = new Guest("John", "Doe");
    }

    @Test
    public void testConstructor() {
        assertNotNull(guest); // The object should not be null
        assertEquals("John", guest.getName()); // Ensure the name is set correctly
        assertEquals("Doe", guest.getSurname()); // Ensure the surname is set correctly
    }

    @Test
    public void testGetName() {
        assertEquals("John", guest.getName());
    }

    @Test
    public void testGetSurname() {
        assertEquals("Doe", guest.getSurname());
    }

    @Test
    public void testSetName() {
        guest.setName("Alice");
        assertEquals("Alice", guest.getName());
    }

    @Test
    public void testSetSurname() {
        guest.setSurname("Smith");
        assertEquals("Smith", guest.getSurname());
    }

    @Test
    public void testSetNameAndSurname() {
        guest.setName("Bob");
        guest.setSurname("Brown");
        assertEquals("Bob", guest.getName());
        assertEquals("Brown", guest.getSurname());
    }
}
