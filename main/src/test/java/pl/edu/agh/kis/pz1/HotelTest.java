package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    private Hotel hotel;
    private Room room1, room2, room3;

    @BeforeEach
    void setUp() {
        // Initialize the hotel and floors
        hotel = new Hotel();

        // Create some rooms
        room1 = new Room(0, 101, 100.0f, 2);  // Floor 0, Room 101
        room2 = new Room(1, 201, 120.0f, 2);  // Floor 1, Room 201
        room3 = new Room(1, 202, 120.0f, 2);  // Floor 1, Room 202

        // Initialize floors with MyMap and add rooms
        MyMap<Integer, Room> floor1= new MyMap<>();
        MyMap<Integer, Room> floor2= new MyMap<>();
        MyMap<Integer, Room> floor0= new MyMap<>();
        floor1.put(101, room1);
        floor2.put(201, room2);
        floor2.put(202, room3);

        // Add floors to the hotel
        hotel.getFloors().add(floor0);
        hotel.getFloors().add(floor1);
        hotel.getFloors().add(floor2);

        System.out.println("Number of floors: " + hotel.getNumberOfFloors());
        for (MyMap<Integer,Room> floor: hotel.getFloors()) {
            for(Object i : floor.keys()) {
                System.out.println(i);
                floor.get(i).showInfo();
            }
        }
    }

    @Test
    void testGetRoomByNumber_WhenRoomExists() {
        Room foundRoom = hotel.getRoomByNumber(101);

        // Verify that the room is found and is correct
        assertNotNull(foundRoom, "Room 101 should exist.");
        assertEquals(room1, foundRoom, "The room returned should be the same as the room with number 101.");
    }

    @Test
    void testGetRoomByNumber_WhenRoomDoesNotExist() {
        Room foundRoom = hotel.getRoomByNumber(399);

        // Verify that the room does not exist and return null
        assertNull(foundRoom, "Room 399 should not exist.");
    }

    @Test
    void testGetRoomByNumber_WhenRoomIsOnDifferentFloor() {
        Room foundRoom = hotel.getRoomByNumber(202);

        // Verify that the room is correctly retrieved from the second floor
        assertNotNull(foundRoom, "Room 202 should exist.");
        assertEquals(room3, foundRoom, "The room returned should be the same as the room with number 202.");
    }

    @Test
    void testGetNumberOfFloors() {
        int numberOfFloors = hotel.getNumberOfFloors();

        // Verify that the number of floors in the hotel is correct
        assertEquals(3, numberOfFloors, "Hotel should have 3 floors.");
    }

    @Test
    void testGetNumberOfFloors_WhenNoFloors() {
        // Create a hotel with no floors
        Hotel emptyHotel = new Hotel();
        int numberOfFloors = emptyHotel.getNumberOfFloors();

        // Verify that the hotel with no floors has a floor count of 0
        assertEquals(0, numberOfFloors, "Hotel with no floors should return 0.");
    }
}
