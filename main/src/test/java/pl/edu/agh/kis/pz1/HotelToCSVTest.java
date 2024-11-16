package pl.edu.agh.kis.pz1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.hotelToCSV;
import pl.edu.agh.kis.pz1.main.model.*;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HotelToCSVTest {
    private Hotel hotel;
    private hotelToCSV hotelToCSVCommand;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialize hotel and rooms
        hotel = new Hotel();
        MyMap<Integer, Room> zeroFloor = new MyMap<>();
        hotel.getFloors().add(zeroFloor);

        // Create floors with rooms and guests
        MyMap<Integer, Room> firstFloor = new MyMap<>();
        Room room101 = new Room(1, 101, 400, 2);
        room101.setMainGuest(new Guest("John", "Doe"));
        ArrayList<Guest> otherGuests101 = new ArrayList<>();
        otherGuests101.add(new Guest("Jane", "Smith"));
        room101.setOtherGuests(otherGuests101);
        room101.setDateOfCheckin(java.time.Instant.now());
        room101.setLengthOfStay(3);
        room101.setAdditionalData("Extra pillow");
        firstFloor.put(101, room101);

        MyMap<Integer, Room> secondFloor = new MyMap<>();
        Room room201 = new Room(2, 201, 300, 2);
        secondFloor.put(201, room201);

        // Add floors to the hotel
        hotel.getFloors().add(firstFloor);
        hotel.getFloors().add(secondFloor);

        // Initialize the hotelToCSV command
        hotelToCSVCommand = new hotelToCSV();

        // Redirect system output to capture it for assertions (if needed)
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    // Helper method to simulate user input
    private void simulateSystemIn(String input) {
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testHotelToCSV() throws IOException {
        // Simulate user input for the file name
        simulateSystemIn("test_hotel.csv");

        // Execute the hotelToCSV command (writing data to the file)
        hotelToCSVCommand.execute(hotel);

        // Verify that the file was created
        File outputFile = new File("test_hotel.csv");
        assertTrue(outputFile.exists(), "CSV file should be created.");

        // Read the CSV file to check its contents
        try (FileReader fileReader = new FileReader(outputFile)) {
            char[] buffer = new char[(int) outputFile.length()];
            fileReader.read(buffer);  //for headers
            String fileContents = new String(buffer);

            // Verify that the file contents include the expected data
            assertTrue(fileContents.contains("Floor"), "File should contain Floor header.");
            assertTrue(fileContents.contains("RoomNumber"), "File should contain RoomNumber header.");
            assertTrue(fileContents.contains("Capacity"), "File should contain Capacity header.");
            assertTrue(fileContents.contains("Price"), "File should contain Price header.");
            assertTrue(fileContents.contains("IsFree"), "File should contain IsFree header.");
            assertTrue(fileContents.contains("John Doe"), "File should contain main guest's name.");
            assertTrue(fileContents.contains("Jane Smith"), "File should contain other guest's name.");
            assertTrue(fileContents.contains("Extra pillow"), "File should contain additional data.");
        }

        // Clean up by deleting the created test file
        outputFile.delete();
    }

}
