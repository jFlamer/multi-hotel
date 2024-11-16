package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.View;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ViewTest {
    private Hotel hotel;
    private View viewCommand;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialize hotel and rooms
        hotel = new Hotel();
        MyMap<Integer, Room> zeroFloor = new MyMap<>();
        hotel.getFloors().add(zeroFloor);

        // Create floors with rooms
        MyMap<Integer, Room> firstFloor = new MyMap<>();
        firstFloor.put(101, new Room(1, 101, 400, 3));
        firstFloor.put(102, new Room(1, 102, 300, 2));

        MyMap<Integer, Room> secondFloor = new MyMap<>();
        secondFloor.put(201, new Room(2, 201, 500, 5));
        secondFloor.put(202, new Room(2, 202, 600, 4));

        // Add floors to the hotel
        hotel.getFloors().add(firstFloor);
        hotel.getFloors().add(secondFloor);

        // Initialize the View command
        viewCommand = new View();

        // Redirect system output to capture it for assertions
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testViewRoomWithGuests() {
        // Set up guests for room 101
        Room room101 = hotel.getRoomByNumber(101);
        Guest mainGuest = new Guest("John", "Doe");
        room101.setMainGuest(mainGuest);
        Guest otherGuest = new Guest("Jane", "Smith");
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(otherGuest);
        room101.setOtherGuests(guests);
        room101.setDateOfCheckin(Instant.parse("2024-11-01T10:00:00Z"));
        room101.setLengthOfStay(4);
        room101.setAdditionalData("Some information");

        // Simulate entering the room number 101
        String input = "101\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Execute the View command
        viewCommand.execute(hotel);

        // Capture output
        String output = outputStream.toString();

        // Verify that room 101 information and guests' details are displayed
        assertTrue(output.contains("Room number: 101"), "Output should include the room number.");
        assertTrue(output.contains("Room price: 400"), "Output should include the room price.");
        assertTrue(output.contains("Room is occupied"), "Output should include the room availability.");
        assertTrue(output.contains("Capacity: 3"), "Output should include the room capacity.");
        assertTrue(output.contains("Main guest: John Doe"), "Output should include the main guest's name.");
        assertTrue(output.contains("Extra guest: Jane Smith"), "Output should indicate the presence of other guests.");
        assertTrue(output.contains("Date of checkin: 2024-11-01T10:00:00Z"), "Output should include the date of checkin.");
        assertTrue(output.contains("Length of stay: 4"), "Output should include the length of stay.");
        assertTrue(output.contains("Additional data: Some information"), "Output should include the additional information.");
    }

    @Test
    void testViewRoomWithNoGuests() {
        // Simulate entering the room number 102 (with no guests)
        String input = "102\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Execute the View command
        viewCommand.execute(hotel);

        // Capture output
        String output = outputStream.toString();

        // Verify that room 102 information is displayed and no guests are listed
        assertTrue(output.contains("Room number: 102"), "Output should include the room number.");
        assertTrue(output.contains("Room price: 300"), "Output should include the room price.");
        assertTrue(output.contains("Capacity: 2"), "Output should include the room capacity.");
        assertTrue(output.contains("Room is free"), "Output should include the availability.");
        assertFalse(output.contains("Main guest:"), "Output should not include a main guest.");
        assertFalse(output.contains("Other guests:"), "Output should not list any other guests.");
    }

    @Test
    void testViewRoomWhenRoomDoesNotExist() {
        // Simulate entering a non-existing room number (e.g., 999)
        String input = "999\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Execute the View command
        viewCommand.execute(hotel);

        // Capture output
        String output = outputStream.toString();

        // Verify that "Room not found" message is displayed
        assertTrue(output.contains("Room not found"), "Output should indicate that the room is not found.");
    }

    @Test
    void testViewRoomWithGuestsOnSecondFloor() {
        // Set up guests for room 202 on the second floor
        Room room202 = hotel.getFloors().get(2).get(202);
        Guest mainGuest = new Guest("Alice", "Green");
        room202.setMainGuest(mainGuest);
        Guest otherGuest = new Guest("Bob", "Brown");
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(otherGuest);
        room202.setOtherGuests(guests);
        room202.setDateOfCheckin(Instant.parse("2024-11-11T10:00:00Z"));
        room202.setLengthOfStay(3);
        room202.setAdditionalData("Some information");

        // Simulate entering the room number 202
        String input = "202\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Execute the View command
        viewCommand.execute(hotel);

        // Capture output
        String output = outputStream.toString();

        // Verify that room 202 information and guests' details are displayed
        assertTrue(output.contains("Room number: 202"), "Output should include the room number.");
        assertTrue(output.contains("Room price: 600"), "Output should include the room price.");
        assertTrue(output.contains("Capacity: 4"), "Output should include the room capacity.");
        assertTrue(output.contains("Main guest: Alice Green"), "Output should include the main guest's name.");
        assertTrue(output.contains("Extra guest: Bob Brown"), "Output should indicate the presence of other guests.");
        assertTrue(output.contains("Date of checkin: 2024-11-11T10:00:00Z"), "Output should include the date of checkin.");
        assertTrue(output.contains("Length of stay: 3"), "Output should include the length of stay.");
        assertTrue(output.contains("Additional data: Some information"), "Output should include the additional information.");
    }

}
