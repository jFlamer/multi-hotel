package pl.edu.agh.kis.pz1.main.commands;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

@Data
public class CheckIn extends Command {

    private boolean testMode = false;
    private int roomNumber;
    private Instant checkInTime;
    private Guest mainGuest;
    private ArrayList<Guest> guests;
    private int lengthOfStay;
    private String additionalInfo;


    public Guest scanGuestData(int capacityOfRoom, int guestNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide name of guest " + guestNumber + "/" + capacityOfRoom + ": ");
        String guestName = scanner.nextLine();
        if (StringUtils.isEmpty(guestName)) {
            return null;
        }
        System.out.println("Provide surname of guest " + guestNumber + "/" + capacityOfRoom + ": ");
        String guestSurname = scanner.nextLine();
        return new Guest(guestName, guestSurname);
    }

    @Override
    public void execute(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        if (!testMode) {
            System.out.println("Room number: ");
            int scanRoomNumber = scanner.nextInt();
            scanner.nextLine();
            this.setRoomNumber(scanRoomNumber);
        }
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return;
        }
        if (currentRoom.isFree()){
            if (!testMode){
                this.setMainGuest(scanGuestData(currentRoom.getCapacity(), 1));
                ArrayList<Guest> ScanOtherGuests = new ArrayList<>();
                for (int i = 1; i < currentRoom.getCapacity(); i++) {
                    ScanOtherGuests.add(scanGuestData(currentRoom.getCapacity(), i + 1));
                }
                this.setGuests(ScanOtherGuests);

                System.out.println("Date of check-in (YYYY-MM-DD): ");
                String checkInDate = scanner.nextLine() + "T00:00:00Z";
                if (StringUtils.isEmpty(checkInDate)) {
                    this.setCheckInTime(Instant.now().truncatedTo(ChronoUnit.DAYS));
                } else {
                    this.setCheckInTime(Instant.parse(checkInDate));
                }

                System.out.println("Length of stay: ");
                int ScanLengthOfStay = scanner.nextInt();
                scanner.nextLine();
                this.setLengthOfStay(ScanLengthOfStay);

                System.out.println("Additional info:");
                String scanAdditionalInfo = scanner.nextLine();
                this.setAdditionalInfo(scanAdditionalInfo);
            }
            currentRoom.setMainGuest(mainGuest);
            currentRoom.setOtherGuests(guests);
            currentRoom.setDateOfCheckin(checkInTime);
            currentRoom.setLengthOfStay(lengthOfStay);
            currentRoom.setAdditionalData(additionalInfo);

            System.out.println("Room has been successfully booked!");
            return;
        } else {
            System.out.println("Room is occupied");
            return;
        }
    }
}
