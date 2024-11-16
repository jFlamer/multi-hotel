package pl.edu.agh.kis.pz1.main.commands;

import lombok.*;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.util.Scanner;

/**
 * The {@code CheckOut} class handles the check-out process for guests in the hotel.
 * It allows users to check out a guest by marking the room as free, resetting the relevant
 * booking data, and calculating the total price for the stay.
 * <p>
 * The class provides functionality to:
 * <ul>
 *     <li>Prompt the user for the room number to check out from.</li>
 *     <li>Check whether the room is currently occupied or free.</li>
 *     <li>Update room data to mark it as free and reset guest details upon check-out.</li>
 *     <li>Calculate and display the total price of the stay upon check-out.</li>
 * </ul>
 * It can operate in test mode, which allows bypassing the user input for automated testing.
 * </p>
 */
@Data
public class CheckOut extends Command {

    /** A flag indicating whether the command is in test mode. If set to {@code true}, input scanning is skipped. */
    private boolean testMode = false;

    /** The room number to check out from. */
    private int roomNumber;

    /**
     * Executes the check-out process for a guest from a specified room.
     * This method interacts with the user to gather the room number, and then it performs
     * the check-out procedure, which includes marking the room as free, resetting booking data,
     * and calculating the total price for the guest's stay.
     * If the room is already free, a message is displayed to indicate that the room is not occupied.
     * If the room is found and occupied, the system updates the room's status and displays the total price.
     *
     * @param hotel The {@link Hotel} object representing the hotel with rooms to manage.
     */
    @Override
    public void execute(Hotel hotel) {
        if (!testMode) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Room number: ");
            this.roomNumber = scanner.nextInt();
        }
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return;
        }
        if (!currentRoom.isFree()){
            currentRoom.setFree();
            currentRoom.setAdditionalData(null);
            float totalPrice = currentRoom.totalPrice();
            System.out.println("Total price: " + totalPrice);
            currentRoom.setDateOfCheckin(null);
            currentRoom.setLengthOfStay(0);
            return;
        }
        else {
            System.out.println("Room is not occupied");
            return;
        }
    }
}
