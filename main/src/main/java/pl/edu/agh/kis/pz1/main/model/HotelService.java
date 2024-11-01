package pl.edu.agh.kis.pz1.main.model;

import org.apache.commons.lang3.StringUtils;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class HotelService {

    public HotelService() {}

    private Room createRandRoom(int floorNumber, int onFloorNumber) {
        Random random = new Random();
        Room randRoom = new Room(floorNumber, onFloorNumber,
                (((random.nextInt(40001) + 10000) / 100l)),random.nextInt(5) + 1);
        if (floorNumber*100 + onFloorNumber == 101) {
            randRoom.setCapacity(1);
        }
        return randRoom;

    }

    private MyMap<Integer, Room> createRandFloor(int floorNumber) {
        MyMap<Integer, Room> randFloor = new MyMap<>();
        Random random = new Random();
        int numberOfRooms = random.nextInt(21) + 1;
        for (int i = 1; i <= numberOfRooms; i++) {
            randFloor.put(floorNumber*100 + i, createRandRoom(floorNumber, i));
        }
        return randFloor;
    }

    public Hotel createHotel() {
        int numberOfFloors = 8;
        Hotel testHotel = new Hotel();
        for (int i = 0; i < numberOfFloors; i++) {
            testHotel.getFloors().add(createRandFloor(i));
        }
        return testHotel;
    }


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
     * Check in a guest to a room with a provided number
     * scan data of a main guest, optionally additional guests if capacity allows
     * mark the room as occupied and save check in date if provided else save current date.
     * save also length of stay
     * @return false if the room is already occupied, true if check in is possible
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
}
