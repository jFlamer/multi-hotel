package pl.edu.agh.kis.pz1.main.model;

import lombok.*;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a hotel that contains multiple floors and rooms.
 * Each floor is represented by a MyMap of rooms, where the key is the room number and the value is the room object.
 * The hotel provides methods for retrieving rooms by their number and managing floors.
 * <p>
 * The Hotel class is responsible for organizing the hotel structure, with each floor containing a collection of rooms.
 * </p>
 */
@Data
public class Hotel {

    /**
     * ArrayList containing each floor, represented by a MyMap of rooms on a given floor.
     * Each MyMap contains room numbers as keys and the corresponding Room objects as values.
     */
    private ArrayList<MyMap<Integer, Room>> floors;

    /**
     * Constructs a new Hotel object with an empty list of floors.
     * The hotel initially has no floors, but floors can be added dynamically.
     */
    public Hotel() {
        this.floors = new ArrayList<>();
    }

    /**
     * Retrieves a room by its room number.
     * This method looks for the room in the floor corresponding to the room number.
     *
     * @param number the room number to search for
     * @return the Room object if found, null otherwise
     */
    public Room getRoomByNumber(int number) {
        int roomsFloor = number/100;
        if (roomsFloor + 1 > floors.size()) {
            return null;
        }
        if (floors.get(roomsFloor).contains(number)) {
            Room currentRoom = floors.get(roomsFloor).get(number);
            return currentRoom;
        } else {
            return null;
        }
    }

    /**
     * Returns the total number of floors in the hotel.
     *
     * @return the number of floors in the hotel
     */
    public int getNumberOfFloors() {
        return floors.size();
    }

}
