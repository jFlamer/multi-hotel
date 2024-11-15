package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;

import java.util.Scanner;

public class HotelSystem {
    public Command checkIn = new CheckIn();
    public Command checkOut = new CheckOut();
    public Command getFromCSV = new hotelFromCSV();
    public Command saveToCSV = new hotelToCSV();
    public Command listRooms = new List();
    public Command prices = new Prices();
    public Command view = new View();
    public Command exit = new Exit();

    private void printCommands() {
        System.out.print("""
                Please choose a command to execute:
                1. Check In - Use this command to check in a guest to a room.
                2. Check Out - Use this command to check out a guest from a room.
                3. Get from CSV - Load hotel data from a CSV file.
                4. Save to CSV - Save hotel data to a CSV file.
                5. List Rooms - Display a list of all rooms and their statuses.
                6. Prices - View room pricing information.
                7. View - View detailed information about a specific room.
                8. Exit - Exit application.
                Enter the number corresponding to your choice:
                """);

    }

    public void manageHotel(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        int buffor = 100;
        while (buffor > 0) {
            printCommands();
            opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    checkIn.execute(hotel);
                    break;
                case 2:
                    checkOut.execute(hotel);
                    break;
                case 3:
                    getFromCSV.execute(hotel);
                    break;
                case 4:
                    saveToCSV.execute(hotel);
                    break;
                case 5:
                    listRooms.execute(hotel);
                    break;
                case 6:
                    prices.execute(hotel);
                    break;
                case 7:
                    view.execute(hotel);
                    break;
                case 8:
                    exit.execute(hotel);
                    break;
                default:
                    System.out.println("Invalid command");
            }
            buffor -= 1;
        }
    }

}
