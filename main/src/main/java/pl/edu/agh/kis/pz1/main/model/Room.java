package pl.edu.agh.kis.pz1.main.model;


import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Represents a room in a hotel.
 * Each room has specific attributes related to its location, pricing, and occupancy.
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
     *
     * @param floorNumber         the floor number where the room is located
     * @param roomNumberInHotel   the room number within the specific floor
     * @param price               the price per night for staying in the room
     * @param capacity            the maximum number of guests that can stay in the room
     */
    public Room(int floorNumber, int roomNumberInHotel, float price, int capacity) {
        this.floorNumber = floorNumber;
        this.price = price;
        this.capacity = capacity;
        this.roomNumberOnFloor = roomNumberInHotel - floorNumber*100;
        this.roomNumberInHotel = roomNumberInHotel;
    }


    public boolean isFree(){
        return mainGuest == null;
    }

    public void setFree(){
        mainGuest = null;
    }

    public float totalPrice(){
        float totalPrice = 0;
        int numberOfDays = (int) ChronoUnit.DAYS.between(this.dateOfCheckin, Instant.now());
        totalPrice = price*numberOfDays;
        return totalPrice;
    }

    public boolean showInfo(){
        System.out.println("Room number: " + roomNumberInHotel);
        System.out.println("Room price: " + price);
        System.out.println("Capacity: " + capacity);
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
            if(!StringUtils.isEmpty(additionalData)) {
                System.out.println("Additional data: " + additionalData);
            }
        }
        else {
            System.out.println("Room is free");
        }
        return true;
    }
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
