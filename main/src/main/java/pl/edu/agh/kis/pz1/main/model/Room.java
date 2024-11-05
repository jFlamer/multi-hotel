package pl.edu.agh.kis.pz1.main.model;


import java.time.Instant;
import java.util.ArrayList;

/**
 * Represents a room in a hotel.
 * Each room has specific attributes related to its location, pricing, and occupancy.
 */
public class Room {
    private int floorNumber;
    private int roomNumberOnFloor;
    private int roomNumberInHotel;
    private float price;
    private int capacity;
    private Guest mainGuest;
    private ArrayList<Guest> otherGuests;
    private Instant dateOfCheckin;

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    private String additionalData;

    public int getLengthOfStay() {
        return lengthOfStay;
    }

    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    private int lengthOfStay;

    /**
     * Constructs a new Room object with specified attributes.
     *
     * @param floorNumber         the floor number where the room is located
     * @param roomNumberOnFloor   the room number within the specific floor
     * @param price               the price per night for staying in the room
     * @param capacity            the maximum number of guests that can stay in the room
     */
    public Room(int floorNumber, int roomNumberOnFloor, float price, int capacity) {
        this.floorNumber = floorNumber;
        this.price = price;
        this.capacity = capacity;
        this.roomNumberOnFloor = roomNumberOnFloor;
        this.roomNumberInHotel = floorNumber*100 + roomNumberOnFloor;
    }



    public int getFloorNumber() {
        return floorNumber;
    }

    public float getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public Guest getMainGuest() {
        return mainGuest;
    }

    public ArrayList<Guest> getOtherGuests() {
        return otherGuests;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMainGuest(Guest mainGuest) {
        this.mainGuest = mainGuest;
    }

    public void setOtherGuests(ArrayList<Guest> otherGuests) {
        this.otherGuests = otherGuests;
    }

    public void setDateOfCheckin(Instant dateOfCheckin) {
        this.dateOfCheckin = dateOfCheckin;
    }

    public boolean isFree(){
        return mainGuest == null;
    }
}
