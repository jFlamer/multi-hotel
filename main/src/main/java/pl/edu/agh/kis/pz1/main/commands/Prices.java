package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

/**
 * The {@code Prices} class is responsible for displaying the price information of all rooms in the hotel, organized by floor.
 * It iterates through all the floors of the hotel and displays the price for each room within each floor.
 * <p>
 * This command is typically used by hotel staff or administrators to view room pricing across all hotel floors.
 * </p>
 */
public class Prices extends Command{

    /**
     * Executes the command to list the price of each room in the hotel by floor.
     * For each floor, it prints the room number and the corresponding price.
     * This provides a simple overview of the pricing structure of the hotel.
     *
     * The price of each room is retrieved using the {@link Room#getPrice()} method, which returns the room's price.
     *
     * @param hotel The hotel whose room prices are to be listed.
     */
    @Override
    public void execute(Hotel hotel) {
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
}
