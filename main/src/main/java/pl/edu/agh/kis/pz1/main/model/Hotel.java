package pl.edu.agh.kis.pz1.main.model;

import pl.edu.agh.kis.pz1.util.MyMap;

import java.util.ArrayList;
import java.util.Scanner;


public class Hotel {

    /**
     * ArrayList containing each floor with a MyMap of rooms on a given floor.
     */
    private ArrayList<MyMap<Integer, Room>> floors;

    public Hotel() {
        this.floors = new ArrayList<>();
    }

    public Room getRoomByNumber(int number) {
        int roomsFloor = number/100;
        if (floors.get(roomsFloor).contains(number)) {
            Room currentRoom = floors.get(roomsFloor).get(number);
            return currentRoom;
        } else {
            return null;
        }
    }

    public ArrayList<MyMap<Integer, Room>> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<MyMap<Integer, Room>> floors) {
        this.floors = floors;
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

}
