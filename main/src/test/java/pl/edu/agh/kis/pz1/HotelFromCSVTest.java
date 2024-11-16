package pl.edu.agh.kis.pz1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.hotelFromCSV;
import pl.edu.agh.kis.pz1.main.model.*;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HotelFromCSVTest {

    private Hotel hotel;
    private hotelFromCSV command;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        command = new hotelFromCSV();
    }

    // Helper method to create a temporary CSV file with content
    private File createTemporaryCSVFile() throws IOException {
        String csvContent = "Floor,RoomNumber,Capacity,Price,IsFree,MainGuest,OtherGuests,DateOfCheckin,LengthOfStay,AdditionalData\n" +
                "0,101,2,100.0,true,John Doe,,2024-12-01T00:00:00Z,3,No additional data\n" +
                "1,201,3,150.0,false,Jane Smith,Tom Jones,2024-12-05T00:00:00Z,2,Special request\n";

        File tempFile = File.createTempFile("hotel_data_", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(csvContent);
        }
        return tempFile;
    }

    // Helper method to simulate System.in input for the file name
    private void simulateSystemIn(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }


    // Test case 1: Hotel is populated correctly when the CSV is valid
    @Test
    void testHotelFromCSVValidData1() throws IOException {
        // Create a temporary CSV file with test data
        File tempFile = createTemporaryCSVFile();

        // Simulate user input for the file name (simulate file path)
        simulateSystemIn(tempFile.getAbsolutePath());

        // Execute the command
        command.execute(hotel);

        // Verify that rooms and guests have been properly added
        assertEquals(2, hotel.getFloors().size(), "Hotel should have 2 floors.");

        MyMap<Integer, Room> firstFloor = hotel.getFloors().get(0);
        assertNotNull(firstFloor.get(101), "Room 101 should be present on the first floor.");
        Room room101 = firstFloor.get(101);
        assertEquals(100.0, room101.getPrice(), "Room 101 price should be 100.");
        assertEquals(2, room101.getCapacity(), "Room 101 capacity should be 2.");
        assertFalse(room101.isFree(), "Room 101 shouldn't be free.");
        assertEquals("John", room101.getMainGuest().getName(), "Main guest for room 101 should be John.");
        assertEquals("Doe", room101.getMainGuest().getSurname(), "Main guest surname for room 101 should be Doe.");

        MyMap<Integer, Room> secondFloor = hotel.getFloors().get(1);
        assertNotNull(secondFloor.get(201), "Room 201 should be present on the second floor.");
        Room room201 = secondFloor.get(201);
        assertFalse(room201.isFree(), "Room 201 should be occupied.");
        assertEquals(150.0, room201.getPrice(), "Room 201 price should be 150.");
        assertEquals(3, room201.getCapacity(), "Room 201 capacity should be 3.");
        assertEquals("Jane", room201.getMainGuest().getName(), "Main guest for room 201 should be Jane.");
        assertEquals("Smith", room201.getMainGuest().getSurname(), "Main guest surname for room 201 should be Smith.");
        assertEquals(1, room201.getOtherGuests().size(), "Room 201 should have 1 additional guest.");
    }

    // Test case 2: Test when the CSV file is empty
    @Test
    void testHotelFromCSVEmptyData() throws IOException {
        // Create a temporary empty CSV file
        File emptyFile = File.createTempFile("empty_hotel_data_", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(emptyFile))) {
            // No content written
        }

        // Simulate user input for the file name (simulate file path)
        simulateSystemIn(emptyFile.getAbsolutePath());

        // Execute the command
        command.execute(hotel);

        // Check if no rooms were added
        assertTrue(hotel.getFloors().isEmpty(), "Hotel should have no floors.");
    }

    // Test case 3: Handle non-existent file gracefully
    @Test
    void testHotelFromCSVFileNotFound() throws IOException {
        // Simulate user input for a non-existent file
        simulateSystemIn("non_existent_file.csv");

        // Redirect the output stream to capture the printed messages
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Execute the command
        command.execute(hotel);

        // Capture the output and verify the error message is printed
        String output = outputStream.toString();
        assertTrue(output.contains("Error reading the file: " + "non_existent_file.csv"),
                "Output should include error message for non-existent file.");

        // Hotel should not be changed (no rooms should be added)
        assertTrue(hotel.getFloors().isEmpty(), "Hotel should have no floors if the file doesn't exist.");
    }
}
