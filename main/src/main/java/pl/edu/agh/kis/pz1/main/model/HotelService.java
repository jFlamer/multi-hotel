package pl.edu.agh.kis.pz1.main.model;

import org.apache.commons.lang3.StringUtils;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


/**
 * Service class that provides functionalities to manage hotel operations,
 * such as creating rooms, initializing a hotel, and checking in guests.
 */
public class HotelService {

    /**
     * Default constructor for HotelService.
     */
    public HotelService() {}

    /**
     * Creates a random room with a specified floor and position on that floor.
     *
     * @param floorNumber the floor number where the room is located.
     * @param onFloorNumber the room's position on the specified floor.
     * @return a Room object with randomly generated properties.
     */
    private Room createRandRoom(int floorNumber, int onFloorNumber) {
        Random random = new Random();
        Room randRoom = new Room(floorNumber, onFloorNumber,
                (((random.nextInt(40001) + 10000) / 100l)),random.nextInt(5) + 1);
        if (floorNumber*100 + onFloorNumber == 101) {
            randRoom.setCapacity(1);
        }
        return randRoom;

    }

    /**
     * Creates a random floor with a specified floor number.
     *
     * @param floorNumber the floor number.
     * @return a MyMap<Integer, Room> object containing randomly generated rooms for the floor.
     */
    private MyMap<Integer, Room> createRandFloor(int floorNumber) {
        MyMap<Integer, Room> randFloor = new MyMap<>();
        Random random = new Random();
        int numberOfRooms = random.nextInt(21) + 1;
        for (int i = 1; i <= numberOfRooms; i++) {
            randFloor.put(floorNumber*100 + i, createRandRoom(floorNumber, i));
        }
        return randFloor;
    }

    /**
     * Initializes a hotel with multiple floors and randomly generated rooms on each floor.
     *
     * @return a Hotel object populated with floors and rooms.
     */
    public Hotel createHotel() {
        int numberOfFloors = 8;
        Hotel testHotel = new Hotel();
        for (int i = 0; i < numberOfFloors; i++) {
            testHotel.getFloors().add(createRandFloor(i));
        }
        return testHotel;
    }

    /**
     * Collects guest data for checking into a room.
     *
     * @param capacityOfRoom the maximum number of guests allowed in the room.
     * @param guestNumber the number of the current guest (for display purposes).
     * @return a Guest object containing the name and surname of the guest.
     */
    public Guest scanGuestData(int capacityOfRoom, int guestNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide name of guest " + guestNumber + "/" + capacityOfRoom + ": ");
        String guestName = scanner.next();
        if (StringUtils.isEmpty(guestName)) {
            return null;
        }
        System.out.println("Provide surname of guest " + guestNumber + "/" + capacityOfRoom + ": ");
        String guestSurname = scanner.next();
        return new Guest(guestName, guestSurname);
    }

    /**
     * Checks in a guest to a specified room number in the hotel.
     * If the room is free, prompts the user to provide guest information,
     * check-in date, length of stay, and any additional information.
     *
     * @param hotel the Hotel object where the guest will be checked in.
     * @return true if the check-in is successful, false if the room is occupied or does not exist.
     */
    public boolean checkIn(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number: ");
        int roomNumber = scanner.nextInt();
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return false;
        }
        if (currentRoom.isFree()){
            currentRoom.setMainGuest(scanGuestData(currentRoom.getCapacity(), 1));
            ArrayList<Guest> otherGuests = new ArrayList<>();
            for (int i = 1; i < currentRoom.getCapacity(); i++) {
                otherGuests.add(scanGuestData(currentRoom.getCapacity(), i + 1));
            }
            currentRoom.setOtherGuests(otherGuests);
            System.out.println("Date of check-in (YYYY-MM-DD): ");
            String checkInDate = scanner.next() + "T00:00:00Z";
            if (StringUtils.isEmpty(checkInDate)) {
                currentRoom.setDateOfCheckin(Instant.now().truncatedTo(ChronoUnit.DAYS));
            } else {
                currentRoom.setDateOfCheckin(Instant.parse(checkInDate));
            }
            System.out.println("Length of stay: ");
            int lengthOfStay = scanner.nextInt();
            currentRoom.setLengthOfStay(lengthOfStay);
            System.out.println("Additional info:");
            scanner.nextLine();
            String additionalInfo = scanner.nextLine();
            currentRoom.setAdditionalData(additionalInfo);
            System.out.println("Room has been successfully booked!");
            return true;
        } else {
            System.out.println("Room is occupied");
            return false;
        }

    }

    public boolean checkOut(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number: ");
        int roomNumber = scanner.nextInt();
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return false;
        }
        if (!currentRoom.isFree()){
            currentRoom.setFree();
            currentRoom.setAdditionalData(null);
            float totalPrice = currentRoom.totalPrice();
            System.out.println("Total price: " + totalPrice);
            return true;
        }
        else {
            System.out.println("Room is not occupied");
            return false;
        }
    }
    public void prices(Hotel hotel) {
        for(int i = 0; i < hotel.getFloors().size(); i++){
            System.out.println("Floor " + i + ": ");
            MyMap<Integer, Room> currentFloor = hotel.getFloors().get(i);
            for(int j = 0; j < currentFloor.keys().size(); j++){
                int roomNumber = (int)currentFloor.keys().get(j);
                Room currentRoom = currentFloor.get(roomNumber);
                System.out.println("Room " + currentRoom.getRoomNumberInHotel()+ " price: " + currentRoom.getPrice());
            }
        }
    }
    public boolean view(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number: ");
        int roomNumber = scanner.nextInt();
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return false;
        }
        else {
            return currentRoom.showInfo();
        }
    }

    public void list(Hotel hotel) {
        for(int i = 0; i < hotel.getFloors().size(); i++){
            System.out.println("Floor " + i + ": ");
            MyMap<Integer, Room> currentFloor = hotel.getFloors().get(i);
            for(int j = 0; j < currentFloor.keys().size(); j++){
                int roomNumber = (int)currentFloor.keys().get(j);
                Room currentRoom = currentFloor.get(roomNumber);
                currentRoom.listRoom();
            }
        }
    }
}
