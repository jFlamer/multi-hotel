package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.List;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    private Hotel hotel;
    private List listCommand;
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
        firstFloor.put(102, new Room(1, 102, 400, 3));

        MyMap<Integer, Room> secondFloor = new MyMap<>();
        secondFloor.put(201, new Room(2,201, 100,6));
        secondFloor.put(202, new Room(2,202, 400, 7));

        // Add floors to the hotel
        hotel.getFloors().add(firstFloor);
        hotel.getFloors().add(secondFloor);

        // Initialize the List command
        listCommand = new List();

        // Redirect system output to capture it for assertions
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testListRooms() {
        // Execute the command
        listCommand.execute(hotel);

        // Verify output contains expected data
        String output = outputStream.toString();
        assertTrue(output.contains("Floor 0:"), "Output should include Floor 0.");
        assertTrue(output.contains("Floor 1:"), "Output should include Floor 1.");
        assertTrue(output.contains("Room number: 101"), "Output should include Room 101 details.");
        assertTrue(output.contains("Room number: 102"), "Output should include Room 102 details.");
        assertTrue(output.contains("Room number: 201"), "Output should include Room 201 details.");
        assertTrue(output.contains("Room number: 202"), "Output should include Room 202 details.");
    }

    @Test
    void testListEmptyHotel() {
        // Create a new empty hotel
        hotel = new Hotel();

        // Execute the command
        listCommand.execute(hotel);

        // Verify output
        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Output should be empty for an empty hotel.");
    }

    @Test
    void testListSingleFloor() {
        // Create a hotel with one floor
        hotel = new Hotel();
        MyMap<Integer, Room> singleFloor = new MyMap<>();
        singleFloor.put(301, new Room(3,301, 200, 2));
        singleFloor.put(302, new Room(3,302, 100, 3));
        hotel.getFloors().add(singleFloor);

        // Execute the command
        listCommand.execute(hotel);

        // Verify output contains expected data for the single floor
        String output = outputStream.toString();
        assertTrue(output.contains("Floor 0:"), "Output should include Floor 0.");
        assertTrue(output.contains("Room number: 301"), "Output should include Room 301 details.");
        assertTrue(output.contains("Room number: 302"), "Output should include Room 302 details.");
    }
}
