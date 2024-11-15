package pl.edu.agh.kis.pz1.main.model;

import lombok.*;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a hotel that contains multiple floors and rooms.
 * Each floor is represented by a MyMap of rooms.
 */
@Data
public class Hotel {

    /**
     * ArrayList containing each floor with a MyMap of rooms on a given floor.
     */
    private ArrayList<MyMap<Integer, Room>> floors;

    /**
     * Constructs a new Hotel object with an empty list of floors.
     */
    public Hotel() {
        this.floors = new ArrayList<>();
    }

    /**
     * Retrieves a room by its room number.
     *
     * @param number the room number to search for
     * @return the Room object if found, null otherwise
     */
    public Room getRoomByNumber(int number) {
        int roomsFloor = number/100;
        if (floors.get(roomsFloor).contains(number)) {
            Room currentRoom = floors.get(roomsFloor).get(number);
            return currentRoom;
        } else {
            return null;
        }
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

}
