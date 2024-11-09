package pl.edu.agh.kis.pz1.main;

import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.HotelService;
import pl.edu.agh.kis.pz1.main.model.Room;
import pl.edu.agh.kis.pz1.util.TextUtils;

/**
 * Przykładowy kod do zajęć laboratoryjnych 2, 3, 4 z przedmiotu: Programowanie zaawansowane 1
 * @author Paweł Skrzyński
 */
public class Main {
    public static void main( String[] args ) {
        HotelService hotelService = new HotelService();
        Hotel hotel = hotelService.createHotel();
        hotelService.checkIn(hotel);
        //hotelService.checkOut(hotel);
        System.out.println("OK");
        hotelService.list(hotel);

    }
}
