package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.util.Scanner;

public class View extends Command {
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
