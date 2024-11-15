package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

public class List extends Command {
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
