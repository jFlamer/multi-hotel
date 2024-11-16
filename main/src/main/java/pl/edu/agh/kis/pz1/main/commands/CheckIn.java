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

/**
 * The {@code CheckIn} class is responsible for handling the check-in process for a guest into a hotel room.
 * It interacts with the user to collect guest details and room information, and then updates the room's status
 * with the provided data.
 * <p>
 * The class provides functionality to:
 * <ul>
 *     <li>Scan guest information, including name, surname, and additional guests.</li>
 *     <li>Check room availability and handle successful or unsuccessful check-ins.</li>
 *     <li>Set the check-in date, length of stay, and any additional information for the booking.</li>
 * </ul>
 * It uses the {@code Hotel} and {@code Room} objects to manage room booking details.
 * The class can operate in test mode, which allows you to bypass interactive input for automated testing.
 * </p>
 */

@Data
public class CheckIn extends Command {

    /** A flag indicating whether the command is in test mode. If set to {@code true}, input scanning is skipped. */
    private boolean testMode = false;

    /** The room number to check into. */
    private int roomNumber;

    /** The check-in time for the room. */
    private Instant checkInTime;

    /** The main guest of the room. */
    private Guest mainGuest;

    /** A list of other guests staying in the room. */
    private ArrayList<Guest> guests;

    /** The length of stay in days for the booking. */
    private int lengthOfStay;

    /** Additional information for the booking. */
    private String additionalInfo;


    /**
     * Scans for guest data, including name and surname, for a given room capacity.
     * This method is used to collect information for each guest, one at a time.
     *
     * @param capacityOfRoom The total capacity of the room (number of guests allowed).
     * @param guestNumber The current guest number being scanned.
     * @return A {@link Guest} object with the scanned guest's details, or {@code null} if no name is provided.
     */
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

    /**
     * Sets the user's check-in date for the room.
     * <p>
     * This method prompts the user to input a check-in date in the format "YYYY-MM-DD".
     * If no date is provided, the check-in date defaults to the current date (truncated to days).
     * </p>
     * <p>
     * The date is converted to an {@code Instant} object with a time of 00:00:00 UTC.
     * </p>
     */
    public String setUserDateOfCheckin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Date of check-in (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        if (StringUtils.isEmpty(checkInDate)) {
            this.setCheckInTime(Instant.now().truncatedTo(ChronoUnit.DAYS));
        } else {
            checkInDate +=  "T00:00:00Z";
            this.setCheckInTime(Instant.parse(checkInDate));
        }
        return checkInDate;
    }

    /**
     * Sets the user's length of stay for the room.
     * <p>
     * This method prompts the user to input the number of days for their stay.
     * The value is stored and used to calculate the total duration of the reservation.
     * </p>
     */
    public int setUserLengthOfStay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Length of stay: ");
        int ScanLengthOfStay = scanner.nextInt();
        scanner.nextLine();
        this.setLengthOfStay(ScanLengthOfStay);
        return ScanLengthOfStay;
    }

    /**
     * Sets the user's additional information related to their stay.
     * <p>
     * This method prompts the user to input the length of stay. (Note: This may be redundant with {@link #setUserLengthOfStay}.)
     * </p>
     * <p>
     * This information is stored for further use in room details or billing.
     * </p>
     */

    public String setUserAdditionalInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Additional data: ");
        String additionalInfoScan = scanner.nextLine();
        this.setAdditionalInfo(additionalInfoScan);
        return additionalInfoScan;
    }

    /**
     * Executes the check-in process for a guest into a specified room.
     * This method interacts with the user to gather the required information:
     * <ul>
     *     <li>Room number</li>
     *     <li>Guest data (name, surname, etc.)</li>
     *     <li>Check-in date</li>
     *     <li>Length of stay</li>
     *     <li>Additional information</li>
     * </ul>
     * After gathering all the necessary details, it updates the room's attributes, such as the main guest,
     * other guests, check-in time, length of stay, and additional information.
     * If the room is already occupied, the check-in process will fail and display a message.
     *
     * @param hotel The {@link Hotel} object representing the hotel with rooms to manage.
     */
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
                setUserDateOfCheckin();
                setUserLengthOfStay();
                setUserAdditionalInfo();
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
