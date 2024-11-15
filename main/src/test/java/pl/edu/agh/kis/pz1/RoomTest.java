package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room room;
    private Guest guest;
    private Guest guest2;

    @BeforeEach
    void setUp() {
        // Setting up the room and creating actual Guest objects
        room = new Room(1, 101, 100.0f, 2);
        guest = new Guest("John", "Doe"); // Creating actual Guest objects
        guest2 = new Guest("Jane", "Smith");
    }

    @Test
    void testIsFree_WhenNoMainGuest() {
        assertTrue(room.isFree(), "Room should be free if no main guest is set.");
    }

    @Test
    void testIsFree_WhenHasMainGuest() {
        room.setMainGuest(guest);
        assertFalse(room.isFree(), "Room should be occupied if a main guest is set.");
    }

    @Test
    void testSetFree() {
        room.setMainGuest(guest); // Setting the room as occupied
        assertFalse(room.isFree(), "Room should be occupied after setting a main guest.");

        room.setFree(); // Making the room free
        assertTrue(room.isFree(), "Room should be free after calling setFree.");
    }

    @Test
    void testTotalPrice() {
        room.setMainGuest(guest);
        room.setDateOfCheckin(Instant.now().minusSeconds(60 * 60 * 24 * 3)); // 3 days ago

        int lengthOfStay = 3;
        room.setLengthOfStay(lengthOfStay);

        float expectedTotalPrice = room.getPrice() * lengthOfStay;
        assertEquals(expectedTotalPrice, room.totalPrice(), "Total price should be calculated correctly.");
    }

    @Test
    void testShowInfo_WhenRoomIsFree() {
        // Capture the output of System.out.println (using a ByteArrayOutputStream)
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        boolean result = room.showInfo();

        // Check if the expected text appears when the room is free
        assertTrue(outContent.toString().contains("Room is free"), "Show info should indicate room is free.");
        assertTrue(result, "showInfo should return true.");
    }

    @Test
    void testShowInfo_WhenRoomIsOccupied() {
        room.setMainGuest(guest); // Making the room occupied
        room.setDateOfCheckin(Instant.now().minusSeconds(60 * 60 * 24 * 5)); // 5 days ago
        room.setLengthOfStay(5);
        room.setOtherGuests(new ArrayList<>() {{
            add(guest2); // Adding another guest
        }});

        // Capture the output of System.out.println (using a ByteArrayOutputStream)
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        boolean result = room.showInfo();

        // Check if the expected text appears when the room is occupied
        assertTrue(outContent.toString().contains("Room is occupied"), "Show info should indicate room is occupied.");
        assertTrue(outContent.toString().contains("Main guest: John Doe"), "Show info should include the main guest.");
        assertTrue(outContent.toString().contains("Extra guest: Jane Smith"), "Show info should include the extra guest.");
        assertTrue(result, "showInfo should return true.");
    }

    @Test
    void testListRoom_WhenRoomIsFree() {
        // Capture the output of System.out.println (using a ByteArrayOutputStream)
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        boolean result = room.listRoom();

        // Check if the expected text appears when the room is free
        assertTrue(outContent.toString().contains("Room is free"), "listRoom should indicate room is free.");
        assertTrue(result, "listRoom should return true.");
    }

    @Test
    void testListRoom_WhenRoomIsOccupied() {
        room.setMainGuest(guest); // Making the room occupied
        room.setDateOfCheckin(Instant.now().minusSeconds(60 * 60 * 24 * 5)); // 5 days ago
        room.setLengthOfStay(5);
        room.setOtherGuests(new ArrayList<>() {{
            add(guest2); // Adding another guest
        }});

        // Capture the output of System.out.println (using a ByteArrayOutputStream)
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        boolean result = room.listRoom();

        // Check if the expected text appears when the room is occupied
        assertTrue(outContent.toString().contains("Room is occupied"), "listRoom should indicate room is occupied.");
        assertTrue(outContent.toString().contains("Main guest: John Doe"), "listRoom should include the main guest.");
        assertTrue(outContent.toString().contains("Extra guest: Jane Smith"), "listRoom should include the extra guest.");
        assertTrue(result, "listRoom should return true.");
    }
}
