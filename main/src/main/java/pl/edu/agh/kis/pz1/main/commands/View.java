package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.util.Scanner;

/**
 * The {@code View} class is responsible for displaying detailed information about a specific room in the hotel.
 * It prompts the user to enter the room number and then displays the room's details if the room exists in the hotel.
 * <p>
 * This command allows hotel staff or administrators to view detailed information about a specific room by its number.
 * </p>
 */
public class View extends Command {

    /**
     * Executes the command to view detailed information about a specific room in the hotel.
     * It prompts the user to input the room number, searches for the room in the hotel, and displays its details if found.
     *
     * If the room does not exist, an error message is shown. If the room exists, the room's information is displayed
     * using the {@link Room#showInfo()} method.
     *
     * @param hotel The hotel from which the room information is to be displayed.
     */
    @Override
    public void execute(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Room number: ");
        int roomNumber = scanner.nextInt();
        Room currentRoom = hotel.getRoomByNumber(roomNumber);
        if (currentRoom == null) {
            System.out.println("Room not found");
            return;
        }
        else {
            currentRoom.showInfo();
        }
    }
}
