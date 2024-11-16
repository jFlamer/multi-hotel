package pl.edu.agh.kis.pz1.main.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.edu.agh.kis.pz1.main.model.Guest;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class hotelToCSV extends Command {

    private String otherguestsToString(Room currentRoom) {
        String otherGuestsString = "";
        if (currentRoom.getOtherGuests() != null && !currentRoom.getOtherGuests().isEmpty()) {
            for(Guest guest : currentRoom.getOtherGuests()) {
                otherGuestsString += guest.getName() + " " + guest.getSurname() + ",";
            }
            otherGuestsString = otherGuestsString.substring(0, otherGuestsString.length() - 1);
        }
        return otherGuestsString;
    }

    private void printRecordToCsv(MyMap<Integer, Room> currentFloor, CSVPrinter printer, int floorNumber) throws IOException {
        for(int j = 0; j < currentFloor.keys().size(); j++) {
            int roomNumber = (int)currentFloor.keys().get(j);
            Room currentRoom = currentFloor.get(roomNumber);
            String otherGuestsString = otherguestsToString(currentRoom);

            printer.printRecord(
                    floorNumber,
                    roomNumber,
                    currentRoom.getCapacity(),
                    currentRoom.getPrice(),
                    currentRoom.isFree(),
                    currentRoom.getMainGuest() != null ? currentRoom.getMainGuest().getInfo() : null,
                    otherGuestsString != null && !otherGuestsString.isEmpty()  ? otherGuestsString : " ",
                    currentRoom.getDateOfCheckin() != null ? currentRoom.getDateOfCheckin(): " ",
                    currentRoom.getLengthOfStay() != 0 ? currentRoom.getLengthOfStay() : " ",
                    currentRoom.getAdditionalData() != null ? currentRoom.getAdditionalData() : null
            );
        }
    }

    @Override
    public void execute(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name: ");
        String fileName = scanner.next();
        try(FileWriter fileWriter = new FileWriter(fileName);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("Floor", "RoomNumber","Capacity", "Price", "IsFree", "MainGuest", "OtherGuests", "DateOfCheckin", "LengthOfStay", "AdditionalData"))){
            for(int i = 0; i < hotel.getFloors().size(); i++) {
                MyMap<Integer, Room> currentFloor = hotel.getFloors().get(i);
                printRecordToCsv(currentFloor, csvPrinter, i);
            }
        } catch (IOException e) {
        }
    }
}
