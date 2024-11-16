package pl.edu.agh.kis.pz1.main.commands;

import lombok.*;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.util.Scanner;

@Data
public class CheckOut extends Command {

    private boolean testMode = false;
    private int roomNumber;

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
