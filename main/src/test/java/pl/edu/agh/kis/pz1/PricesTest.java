package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.commands.Prices;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PricesTest {

    private Hotel hotel;
    private Prices pricesCommand;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Initialize hotel and rooms
        hotel = new Hotel();
        MyMap<Integer, Room> zeroFloor = new MyMap<>();
        hotel.getFloors().add(zeroFloor);

        // Create floors with rooms
        MyMap<Integer, Room> firstFloor = new MyMap<>();
        firstFloor.put(101, new Room(1, 101, 400, 3)); // Price 400
        firstFloor.put(102, new Room(1, 102, 300, 2)); // Price 300

        MyMap<Integer, Room> secondFloor = new MyMap<>();
        secondFloor.put(201, new Room(2, 201, 500, 5)); // Price 500
        secondFloor.put(202, new Room(2, 202, 600, 4)); // Price 600

        // Add floors to the hotel
        hotel.getFloors().add(firstFloor);
        hotel.getFloors().add(secondFloor);

        // Initialize the List command
        pricesCommand = new Prices();

        // Redirect system output to capture it for assertions
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testListRooms() {
        // Setup data
        pricesCommand.execute(hotel);

        // Check if output contains the correct floor and room information
        String output = outputStream.toString();
        // Check if the prices are correctly listed for each room
        assertTrue(output.contains("Floor 0:"), "Output should include Floor 0.");
        assertTrue(output.contains("Room 101 price: 400"), "Output should include Room 101 price of 400.");
        assertTrue(output.contains("Room 102 price: 300"), "Output should include Room 102 price of 300.");
        assertTrue(output.contains("Floor 1:"), "Output should include Floor 1.");
        assertTrue(output.contains("Room 201 price: 500"), "Output should include Room 201 price of 500.");
        assertTrue(output.contains("Room 202 price: 600"), "Output should include Room 202 price of 600.");
    }

    @Test
    void testListEmptyHotel() {
        // Create a new empty hotel and execute the command
        hotel = new Hotel();

        pricesCommand.execute(hotel);

        // Capture output and assert that no prices are listed for an empty hotel
        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Output should be empty for an empty hotel.");
    }

    @Test
    void testListSingleFloor() {
        // Setup hotel with a single floor and execute command
        hotel = new Hotel();
        MyMap<Integer, Room> singleFloor = new MyMap<>();
        singleFloor.put(301, new Room(3, 301, 200, 2)); // Price 200
        singleFloor.put(302, new Room(3, 302, 350, 3)); // Price 350
        hotel.getFloors().add(singleFloor);

        pricesCommand.execute(hotel);

        String output = outputStream.toString();
        assertTrue(output.contains("Floor 0:"), "Output should include Floor 0.");
        assertTrue(output.contains("Room 301 price: 200"), "Room 301 price should be 200.");
        assertTrue(output.contains("Room 302 price: 350"), "Room 302 price should be 350.");
    }
}
