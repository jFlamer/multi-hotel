package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

/**
 * The {@code List} class is responsible for listing all rooms in the hotel, organized by floor.
 * It iterates through all the floors of the hotel and displays the details of each room within each floor.
 * This includes displaying room information such as room number, capacity, price, and whether the room is available or occupied.
 * <p>
 * This command is used to view the status of all rooms in the hotel, making it easier for hotel staff to quickly check room availability.
 * </p>
 */
public class List extends Command {

    /**
     * Executes the command to list all the rooms in the hotel by floor.
     * For each floor, it prints the floor number followed by the details of each room on that floor.
     * This helps the user to easily see the layout of the hotel, room numbers, and their availability status.
     *
     * The room details are retrieved using the {@link Room#listRoom()} method, which is expected to print the room's information.
     *
     * @param hotel The hotel whose room details are to be listed.
     */
    @Override
    public void execute(Hotel hotel) {
        for(int i = 0; i < hotel.getFloors().size(); i++){
            System.out.println("Floor " + i + ": ");
            MyMap<Integer, Room> currentFloor = hotel.getFloors().get(i);
            for(int j = 0; j < currentFloor.keys().size(); j++){
                int roomNumber = (int)currentFloor.keys().get(j);
                Room currentRoom = currentFloor.get(roomNumber);
                currentRoom.listRoom();
            }
        }
    }
}
