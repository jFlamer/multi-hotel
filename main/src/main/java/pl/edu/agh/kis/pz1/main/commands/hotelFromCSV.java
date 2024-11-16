package pl.edu.agh.kis.pz1.main.commands;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import pl.edu.agh.kis.pz1.main.model.*;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code hotelFromCSV} class is responsible for loading hotel data from a CSV file and updating
 * the hotel object with the parsed data. This class reads data such as floor number, room number,
 * capacity, price, guest details, and check-in information from the CSV file and maps them to room
 * objects in the hotel.
 * <p>
 * The CSV file is expected to contain the following columns: "Floor", "RoomNumber", "Capacity",
 * "Price", "IsFree", "MainGuest", "OtherGuests", "DateOfCheckin", "LengthOfStay", and "AdditionalData".
 * </p>
 */
public class hotelFromCSV extends Command {

    /**
     * Helper method to check if a string is non-null, non-empty, and not just a space.
     *
     * @param obj The string to check.
     * @return {@code true} if the string is valid, otherwise {@code false}.
     */
    private boolean correctInfo(String obj){
        return obj != null && !obj.isEmpty() && !" ".equals(obj);
    }


    /**
     * Executes the command to load hotel data from a CSV file. The method prompts the user to enter
     * the file name, reads the file, and parses its contents into rooms which are then added to the hotel.
     * <p>
     * The CSV file should contain rows with room and guest information. For each room, a new {@link Room}
     * object is created and added to the appropriate floor in the hotel.
     * </p>
     *
     * @param hotel The hotel object that will be populated with room data from the CSV file.
     */
    @Override
    public void execute(Hotel hotel){
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name: ");
        String fileName = scanner.next();
        try (FileReader reader = new FileReader(fileName);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            ArrayList<MyMap<Integer, Room>> floors = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                int floorNumber = Integer.parseInt(csvRecord.get("Floor"));
                int roomNumber = Integer.parseInt(csvRecord.get("RoomNumber"));
                int capacity = Integer.parseInt(csvRecord.get("Capacity"));
                float price = Float.parseFloat(csvRecord.get("Price"));
                boolean isFree = Boolean.parseBoolean(csvRecord.get("IsFree"));
                String mainGuestInfo = csvRecord.get("MainGuest");
                String otherGuestsInfo = csvRecord.get("OtherGuests");
                String checkInDate = csvRecord.get("DateOfCheckin");
                String lengthOfStayStr = csvRecord.get("LengthOfStay");
                String additionalData = csvRecord.get("AdditionalData");

                MyMap<Integer, Room> currentFloor = null;
                if (floorNumber + 1 > floors.size()) {
                    currentFloor = new MyMap<>();
                    floors.add(currentFloor);
                }
                currentFloor = floors.get(floorNumber);

                int lengthOfStay = 0;
                if (!" ".equals(lengthOfStayStr)) {
                    lengthOfStay = Integer.parseInt(lengthOfStayStr);
                }

                Guest mainGuest = null;
                if (correctInfo(mainGuestInfo)) {
                    String[] mainGuestInfoArray = mainGuestInfo.split(" ");
                    mainGuest = new Guest(mainGuestInfoArray[0], mainGuestInfoArray[1]);
                }

                ArrayList<Guest> otherGuests = new ArrayList<>();
                if (correctInfo(otherGuestsInfo)) {
                    String[] otherGuestsInfoArray = otherGuestsInfo.split(", ");
                    for (String otherGuest : otherGuestsInfoArray) {
                        String[] guestInfoArray = otherGuest.split(" ");
                        otherGuests.add(new Guest(guestInfoArray[0], guestInfoArray[1]));
                    }
                }

                Instant checkIn = null;
                if (correctInfo(checkInDate)) {
                    checkIn = Instant.parse(checkInDate);
                }

                Room newRoom = new Room(floorNumber, roomNumber, price, capacity);
                newRoom.setMainGuest(mainGuest);
                newRoom.setOtherGuests(otherGuests);
                newRoom.setDateOfCheckin(checkIn);
                newRoom.setLengthOfStay(lengthOfStay);
                newRoom.setAdditionalData(additionalData);
                currentFloor.put(roomNumber, newRoom);
            }
            hotel.setFloors(floors);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + fileName);
        }
    }
}
