package pl.edu.agh.kis.pz1.main.model;


import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Represents a room in a hotel.
 * Each room has specific attributes related to its location, pricing, and occupancy.
 * The class provides functionality for managing guest check-ins, calculating total prices, and displaying room information.
 * <p>
 * Rooms can be free or occupied, and they store information about the main guest, other guests, the check-in date, and additional room details.
 * </p>
 */
@Data
public class Room {

    private int floorNumber;
    private int roomNumberOnFloor;

    private int roomNumberInHotel;

    private float price;

    private int capacity;

    private Guest mainGuest;

    private ArrayList<Guest> otherGuests;

    private Instant dateOfCheckin;

    private String additionalData;

    private int lengthOfStay;

    /**
     * Constructs a new Room object with specified attributes.
     * This constructor initializes a room based on its floor number, room number, price, and capacity.
     *
     * @param floorNumber        The floor number where the room is located
     * @param roomNumberInHotel  The unique room number across the entire hotel
     * @param price              The price per night for staying in the room
     * @param capacity           The maximum number of guests the room can accommodate
     */
    public Room(int floorNumber, int roomNumberInHotel, float price, int capacity) {
        this.floorNumber = floorNumber;
        this.price = price;
        this.capacity = capacity;
        this.roomNumberOnFloor = roomNumberInHotel - floorNumber*100;
        this.roomNumberInHotel = roomNumberInHotel;
    }

    /**
     * Determines whether the room is free (i.e., no main guest).
     *
     * @return true if the room is free, false if the room is occupied by a guest
     */
    public boolean isFree(){
        return mainGuest == null;
    }

    /**
     * Frees up the room by setting the main guest to null.
     * This method is typically used for check-out operations.
     */
    public void setFree(){
        mainGuest = null;
        otherGuests = null;
    }

    /**
     * Calculates the total price for the stay in the room based on the number of days the room has been occupied.
     *
     * @return The total price for the stay, calculated by multiplying the room price by the number of days the guest has stayed
     */
    public float totalPrice(){
        float totalPrice = 0;
        int numberOfDays = (int) ChronoUnit.DAYS.between(this.dateOfCheckin, Instant.now());
        totalPrice = price*numberOfDays;
        return totalPrice;
    }

    /**
     * Displays detailed information about the room, including its occupancy status, guest details, and additional data.
     * This method is typically used to display detailed information for the front desk staff or customer.
     *
     * @return true if the information is displayed successfully
     */
    public boolean showInfo(){
        System.out.println("Room number: " + roomNumberInHotel);
        System.out.println("Room price: " + price);
        System.out.println("Capacity: " + capacity);
        if(mainGuest != null){
            System.out.println("Room is occupied");
            System.out.println("Main guest: " + mainGuest.getName() + " " + mainGuest.getSurname());
            if(otherGuests != null && !otherGuests.isEmpty()){
                for(Guest guest : otherGuests){
                    System.out.println("Extra guest: " + guest.getName() + " " + guest.getSurname());
                }
            }
            System.out.println("Date of checkin: " + dateOfCheckin);
            System.out.println("Length of stay: " + lengthOfStay);
            if(!StringUtils.isEmpty(additionalData)) {
                System.out.println("Additional data: " + additionalData);
            }
        }
        else {
            System.out.println("Room is free");
        }
        return true;
    }

    /**
     * Lists basic information about the room, including its occupancy status, guest details, and check-in date.
     * This method provides a summary of the room's status without showing detailed guest information.
     *
     * @return true if the information is listed successfully
     */
    public boolean listRoom(){
        System.out.println("Room number: " + roomNumberInHotel);
        if(mainGuest != null){
            System.out.println("Room is occupied");
            System.out.println("Main guest: " + mainGuest.getName() + " " + mainGuest.getSurname());
            if(otherGuests != null){
                for(Guest guest : otherGuests){
                    System.out.println("Extra guest: " + guest.getName() + " " + guest.getSurname());
                }
            }
            System.out.println("Date of checkin: " + dateOfCheckin);
            System.out.println("Length of stay: " + lengthOfStay);
        }
        else {
            System.out.println("Room is free");
        }
        return true;
    }
}
