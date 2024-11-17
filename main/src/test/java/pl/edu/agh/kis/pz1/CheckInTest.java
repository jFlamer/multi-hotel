package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.*;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckInTest {

    private Hotel hotel;
    private Room room;
    private CheckIn checkInCommand;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Set up the hotel and room
        hotel = new Hotel();
        room = new Room(1, 101, 100.0f, 2);

        // Initialize MyMap for floor 0 and 1 and add room 101
        MyMap<Integer, Room> floor0 = new MyMap<>();
        MyMap<Integer, Room> floor1 = new MyMap<>();
        floor1.put(101, room);
        hotel.getFloors().add(floor0);
        hotel.getFloors().add(floor1);

        checkInCommand = new CheckIn();
        checkInCommand.setTestMode(true);

        // Redirect System.out to capture print statements
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testCheckInRoomWhenRoomIsFree() {

        checkInCommand.setRoomNumber(101);
        Guest gM = new Guest("John", "Doe");
        checkInCommand.setMainGuest(gM);
        Guest gA = new Guest("Jane", "Smith");
        ArrayList<Guest> aGuests = new ArrayList<>();
        aGuests.add(gA);
        checkInCommand.setGuests(aGuests);
        Instant cT = Instant.parse("2024-11-15T00:00:00Z");
        checkInCommand.setCheckInTime(cT);
        checkInCommand.setLengthOfStay(3);
        checkInCommand.setAdditionalInfo("Additional request");

        // Execute the check-in command
        checkInCommand.execute(hotel);

        // Assertions for the room and guest data
        assertNotNull(room.getMainGuest(), "Main guest should be set.");
        assertEquals("John", room.getMainGuest().getName(), "Main guest name should be John.");
        assertEquals("Doe", room.getMainGuest().getSurname(), "Main guest surname should be Doe.");
        assertEquals(1, room.getOtherGuests().size(), "Other guests should be added.");
        assertEquals("Jane", room.getOtherGuests().get(0).getName(), "Other guest name should be Jane.");
        assertEquals("Smith", room.getOtherGuests().get(0).getSurname(), "Other guest surname should be Smith.");
        assertEquals("2024-11-15T00:00:00Z", room.getDateOfCheckin().toString(), "Check-in date should be set.");
        assertEquals(3, room.getLengthOfStay(), "Length of stay should be set to 3.");
        assertEquals("Additional request", room.getAdditionalData(), "Additional data should be set.");

        // Verify output contains confirmation
        String output = outputStream.toString();
        assertTrue(output.contains("Room has been successfully booked!"), "Output should confirm successful booking.");

    }

    @Test
    void testCheckInRoomWhenRoomIsOccupied() {
        // Precondition: Set the room to be occupied
        Guest existingMainGuest = new Guest("Alice", "Brown");
        ArrayList<Guest> existingGuests = new ArrayList<>();
        existingGuests.add(new Guest("Bob", "Green"));
        room.setMainGuest(existingMainGuest);
        room.setOtherGuests(existingGuests);
        room.setDateOfCheckin(Instant.parse("2024-11-10T00:00:00Z"));
        room.setLengthOfStay(5);

        // Configure the command to attempt check-in on the same room
        checkInCommand.setRoomNumber(101);
        Guest newMainGuest = new Guest("John", "Doe");
        checkInCommand.setMainGuest(newMainGuest);
        ArrayList<Guest> newGuests = new ArrayList<>();
        newGuests.add(new Guest("Jane", "Smith"));
        checkInCommand.setGuests(newGuests);
        checkInCommand.setCheckInTime(Instant.parse("2024-11-15T00:00:00Z"));
        checkInCommand.setLengthOfStay(3);
        checkInCommand.setAdditionalInfo("New request");

        // Execute the check-in command
        checkInCommand.execute(hotel);

        // Assertions: Room details should remain unchanged
        assertNotNull(room.getMainGuest(), "Main guest should still be set.");
        assertEquals("Alice", room.getMainGuest().getName(), "Main guest name should remain Alice.");
        assertEquals("Brown", room.getMainGuest().getSurname(), "Main guest surname should remain Brown.");
        assertEquals(1, room.getOtherGuests().size(), "Other guests should remain unchanged.");
        assertEquals("Bob", room.getOtherGuests().get(0).getName(), "Other guest name should remain Bob.");
        assertEquals("Green", room.getOtherGuests().get(0).getSurname(), "Other guest surname should remain Green.");
        assertEquals("2024-11-10T00:00:00Z", room.getDateOfCheckin().toString(), "Check-in date should remain unchanged.");
        assertEquals(5, room.getLengthOfStay(), "Length of stay should remain unchanged.");
        assertNull(room.getAdditionalData(), "Additional data should remain null.");

        // Verify output contains rejection message
        String output = outputStream.toString();
        assertTrue(output.contains("Room is occupied"), "Output should indicate that the room is already occupied.");
    }

    @Test
    void testScanGuestData() {
        // Simulate user input for guest data
        String simulatedInput = "Jane\nDoe\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method directly
        Guest guest = checkInCommand.scanGuestData(2, 1);

        // Verify guest data is correctly collected
        assertNotNull(guest, "Guest should not be null.");
        assertEquals("Jane", guest.getName(), "Guest name should be Jane.");
        assertEquals("Doe", guest.getSurname(), "Guest surname should be Doe.");
    }

    @Test
    void testScanGuestDataWithEmptyInput() {
        // Simulate empty input for both name and surname
        String simulatedInput = "\n\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method with capacity and guest number
        Guest guest = checkInCommand.scanGuestData(2, 1);

        // Validate that the returned Guest object is null
        assertNull(guest, "Guest should be null if name or surname is empty.");
    }

    @Test
    void testScanGuestDataWithPartialEmptyInput() {
        // Simulate input with name provided but surname empty
        String simulatedInput = "John\n\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method with capacity and guest number
        Guest guest = checkInCommand.scanGuestData(2, 1);

        // Validate that the returned Guest object is null
        assertTrue(guest.getSurname().isEmpty(), "Guest surname should be empty.");
    }

    @Test
    void testSetUserDateOfCheckin() {
        // Simulate user input for a check-in date
        String simulatedInput = "2024-11-16\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method directly
        String checkInDate = checkInCommand.setUserDateOfCheckin();

        // Verify the check-in time is correctly set
        assertNotNull(checkInDate, "Date of check-in should not be null.");
        assertEquals(
                "2024-11-16T00:00:00Z",
                checkInDate,
                "Date of check-in should match the simulated input."
        );
    }

    @Test
    void testSetUserDateOfCheckinWithEmptyInput() {
        // Simulate empty input for the date
        String simulatedInput = "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method
        String returnedDate = checkInCommand.setUserDateOfCheckin();

        // Get the current date (truncated to days) for comparison
        Instant expectedDate = Instant.now().truncatedTo(ChronoUnit.DAYS);

        // Verify the returned date is in the expected format
        assertNotNull(returnedDate, "Returned date should not be null.");
        assertEquals(expectedDate.toString(), checkInCommand.getCheckInTime().toString(),
                "Check-in time should be set to today's date when input is empty.");
    }

    @Test
    void testSetUserDateOfCheckinWithValidInput() {
        // Simulate valid input for the date
        String simulatedInput = "2023-11-15\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Call the method
        String returnedDate = checkInCommand.setUserDateOfCheckin();

        // Expected result
        Instant expectedDate = Instant.parse("2023-11-15T00:00:00Z");

        // Verify the returned date and the room's check-in time
        assertNotNull(returnedDate, "Returned date should not be null.");
        assertEquals("2023-11-15T00:00:00Z", returnedDate,
                "Returned date should match the input date with added time suffix.");
        assertEquals(expectedDate, checkInCommand.getCheckInTime(),
                "Check-in time should be set to the parsed input date.");
    }


    @Test
    void testSetUserLengthOfStay() {
        // Simulate user input for the length of stay
        String simulatedInput = "5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int lengthOfStayScan = checkInCommand.setUserLengthOfStay();

        // Verify the length of stay is correctly set
        assertEquals(
                5,
                lengthOfStayScan,
                "Length of stay should match the simulated input."
        );
    }

    @Test
    void testSetUserAdditionalInfo() {
        // Simulate user input for the length of stay (as described in the method)
        String simulatedInput = "Extra info\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String AdditionalInfoScan = checkInCommand.setUserAdditionalInfo();

        // Verify the length of stay is correctly set
        assertEquals(
                "Extra info",
                AdditionalInfoScan,
                "AdditionalInfo should match the simulated input."
        );
    }


}
