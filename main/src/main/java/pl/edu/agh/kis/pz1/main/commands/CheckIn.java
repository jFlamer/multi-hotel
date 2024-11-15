package pl.edu.agh.kis.pz1.main.commands;

import org.apache.commons.lang3.StringUtils;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckIn extends Command {

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

    @Override
    public void execute(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number: ");
        int roomNumber = scanner.nextInt();
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            //return false;
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
            //return true;
        } else {
            System.out.println("Room is occupied");
            //return false;
        }
    }
}
