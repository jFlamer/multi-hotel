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

public class hotelFromCSV extends Command {
    @Override
    public void execute(Hotel hotel){
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name: ");
        String fileName = scanner.next();
        try (FileReader reader = new FileReader(fileName);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            ArrayList<MyMap<Integer, Room>> floors = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                int floorNumber = Integer.parseInt(record.get("Floor"));
                int roomNumber = Integer.parseInt(record.get("RoomNumber"));
                int capacity = Integer.parseInt(record.get("Capacity"));
                float price = Float.parseFloat(record.get("Price"));
                boolean isFree = Boolean.parseBoolean(record.get("IsFree"));
                String mainGuestInfo = record.get("MainGuest");
                String otherGuestsInfo = record.get("OtherGuests");
                String checkInDate = record.get("DateOfCheckin");
                String lengthOfStayStr = record.get("LengthOfStay");
                String additionalData = record.get("AdditionalData");

                MyMap<Integer, Room> currentFloor = null;
                if (floorNumber + 1 > floors.size()) {
                    currentFloor = new MyMap<>();
                    floors.add(currentFloor);
                } else {
                    currentFloor = floors.get(floorNumber);
                }

                int lengthOfStay = 0;
                if (!" ".equals(lengthOfStayStr)) {
                    lengthOfStay = Integer.parseInt(lengthOfStayStr);
                }

                Guest mainGuest = null;
                if (mainGuestInfo != null && !mainGuestInfo.isEmpty() && !" ".equals(mainGuestInfo)) {
                    String[] mainGuestInfoArray = mainGuestInfo.split(" ");
                    mainGuest = new Guest(mainGuestInfoArray[0], mainGuestInfoArray[1]);
                }

                ArrayList<Guest> otherGuests = new ArrayList<>();
                if (otherGuestsInfo != null && !otherGuestsInfo.isEmpty() && !" ".equals(otherGuestsInfo)) {
                    String[] otherGuestsInfoArray = otherGuestsInfo.split(", ");
                    for (String otherGuest : otherGuestsInfoArray) {
                        String[] guestInfoArray = otherGuest.split(" ");
                        otherGuests.add(new Guest(guestInfoArray[0], guestInfoArray[1]));
                    }
                }

                Instant checkIn = null;
                if (checkInDate != null && !checkInDate.isEmpty() && !" ".equals(checkInDate)) {
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
            System.err.println("Error reading the file: " + fileName);
            e.printStackTrace();
        }
    }
}
