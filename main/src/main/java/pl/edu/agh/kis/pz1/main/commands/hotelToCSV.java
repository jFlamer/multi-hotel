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


/**
 * The {@code hotelToCSV} class is responsible for exporting the hotel's room data to a CSV file.
 * It converts the room and guest details from the hotel system into a CSV format and writes them to a file.
 * The CSV file can later be used for data persistence or sharing.
 * <p>
 * The class includes methods to generate a CSV record for each room, including information about the room number,
 * capacity, price, guest details (main and other guests), check-in date, length of stay, and additional data.
 * </p>
 */
public class hotelToCSV extends Command {

    /**
     * Converts the list of other guests in the room to a string format for CSV output.
     * The guests' names and surnames are concatenated with commas separating each guest.
     *
     * @param currentRoom The room whose other guests are to be converted into a string.
     * @return A string representing the other guests in the room, separated by commas.
     */
    private String otherguestsToString(Room currentRoom) {
        String otherGuestsString = "";
        if (currentRoom.getOtherGuests() != null && !currentRoom.getOtherGuests().isEmpty()) {
            Guest guest = currentRoom.getOtherGuests().get(0);
            if (guest != null) {
                otherGuestsString += guest.getName() + " " + guest.getSurname() + "; ";
                for (int i = 1; i < currentRoom.getOtherGuests().size(); i++) {
                    guest = currentRoom.getOtherGuests().get(i);
                    if (guest != null) {
                        otherGuestsString += guest.getName() + " " + guest.getSurname() + "; ";
                    }
                }
                otherGuestsString = otherGuestsString.substring(0, otherGuestsString.length() - 2);
            }
        }
        return otherGuestsString;
    }

    /**
     * Writes the records of all rooms on a specific floor to the CSV printer.
     * For each room, it gathers information such as room number, capacity, price,
     * guest details, and additional room information and writes it as a CSV record.
     *
     * @param currentFloor The floor whose rooms need to be written to the CSV.
     * @param printer The {@link CSVPrinter} used to print the records to the CSV file.
     * @param floorNumber The number of the floor for the current set of rooms.
     * @throws IOException If an I/O error occurs during the CSV writing process.
     */
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
                    otherGuestsString != null && !otherGuestsString.isEmpty() && !" ".equals(otherGuestsString) ? otherGuestsString : " ",
                    currentRoom.getDateOfCheckin() != null ? currentRoom.getDateOfCheckin(): " ",
                    currentRoom.getLengthOfStay() != 0 ? currentRoom.getLengthOfStay() : " ",
                    currentRoom.getAdditionalData() != null ? currentRoom.getAdditionalData() : null
            );
        }
    }

    /**
     * Executes the process of exporting hotel room data to a CSV file.
     * The user is prompted to enter the desired file name, and the room data (from all hotel floors)
     * is then written to the specified file in CSV format.
     * <p>
     * This method loops over all floors of the hotel, collects data from each room,
     * and writes the data to the CSV file using the {@link CSVPrinter}.
     * </p>
     *
     * @param hotel The hotel whose room data is to be exported to CSV.
     */
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
            System.out.println("Error saving the file: " + fileName);
        }
    }
}
