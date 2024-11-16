package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.CheckOut;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CheckOutTest {
    private Hotel hotel;
    private Room occupiedRoom;
    private Room freeRoom;
    private CheckOut checkOutCommand;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialize hotel and rooms
        hotel = new Hotel();
        occupiedRoom = new Room(1, 101, 400, 1);
        freeRoom = new Room(1, 102, 200, 2);

        // Set occupied room with test data
        occupiedRoom.setMainGuest(new Guest("John", "Doe"));
        occupiedRoom.setDateOfCheckin(java.time.Instant.now());
        occupiedRoom.setLengthOfStay(3);
        occupiedRoom.setAdditionalData("Test data");

        // Add rooms to the hotel
        MyMap<Integer, Room> floor0 = new MyMap<>();
        MyMap<Integer, Room> floor1 = new MyMap<>();
        floor1.put(101, occupiedRoom);
        floor1.put(102, freeRoom);
        hotel.getFloors().add(floor0);
        hotel.getFloors().add(floor1);

        // Initialize the CheckOut command
        checkOutCommand = new CheckOut();
        checkOutCommand.setTestMode(true);

        // Redirect system output to capture it for assertions
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testCheckOutOccupiedRoom() {
        // Simulate user input for room number
        checkOutCommand.setRoomNumber(101);

        // Execute the command
        checkOutCommand.execute(hotel);

        // Verify the room is now free
        assertTrue(occupiedRoom.isFree(), "The room should be free after checkout.");
        assertNull(occupiedRoom.getMainGuest(), "The main guest should be null after checkout.");
        assertNull(occupiedRoom.getDateOfCheckin(), "The check-in date should be null after checkout.");
        assertEquals(0, occupiedRoom.getLengthOfStay(), "The length of stay should be reset to 0.");
        assertNull(occupiedRoom.getAdditionalData(), "Additional data should be cleared after checkout.");

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Total price: "), "Output should include the total price.");
    }

    @Test
    void testCheckOutFreeRoom() {
        checkOutCommand.setRoomNumber(102);

        // Execute the command
        checkOutCommand.execute(hotel);

        // Verify the room is still free
        assertTrue(freeRoom.isFree(), "The room should remain free.");
        assertNull(freeRoom.getMainGuest(), "No guest should be set for a free room.");

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Room is not occupied"), "Output should indicate that the room is not occupied.");
    }

    @Test
    void testCheckOutNonExistentRoom() {
        checkOutCommand.setRoomNumber(999);

        // Execute the command
        checkOutCommand.execute(hotel);

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.contains("Room not found"), "Output should indicate that the room was not found.");
    }
}
