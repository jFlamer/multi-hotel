package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;

import java.util.Scanner;

public class CheckOut extends Command {
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
        if (!currentRoom.isFree()){
            currentRoom.setFree();
            currentRoom.setAdditionalData(null);
            float totalPrice = currentRoom.totalPrice();
            System.out.println("Total price: " + totalPrice);
            currentRoom.setDateOfCheckin(null);
            currentRoom.setLengthOfStay(0);
            //return true;
        }
        else {
            System.out.println("Room is not occupied");
            //return false;
        }
    }
}
