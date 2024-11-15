package pl.edu.agh.kis.pz1.main;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.HotelService;

import java.io.IOException;


public class Main {
    public static void main( String[] args ) {
        HotelService hotelService = new HotelService();
        //Hotel hotel = hotelService.createHotel();
        //hotelService.checkIn(hotel);
        //hotelService.checkOut(hotel);
        //System.out.println("OK");
       // hotelService.list(hotel);
        Hotel hotel = new Hotel();
        try {
            hotelService.HotelCsv(hotel, "hotel_data.csv");
            System.out.println("Hotel utworzony z pliku hotel_data.csv");
        } catch (IOException e) {
            System.err.println("Błąd przy tworzeniu hotelu: " + e.getMessage());
        }
        hotelService.prices(hotel);
        // Zapis stanu hotelu do pliku CSV
        try {
            hotelService.saveToCsv(hotel,"hotel_state.csv");
            System.out.println("Stan hotelu zapisany do hotel_state.csv");
        } catch (IOException e) {
            System.err.println("Błąd przy zapisywaniu stanu hotelu: " + e.getMessage());
        }

    }
}
