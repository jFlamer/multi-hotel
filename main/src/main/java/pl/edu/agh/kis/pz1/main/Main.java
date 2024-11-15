package pl.edu.agh.kis.pz1.main;

import pl.edu.agh.kis.pz1.main.commands.*;
import pl.edu.agh.kis.pz1.main.model.Hotel;
import pl.edu.agh.kis.pz1.main.model.HotelService;

import java.io.IOException;


public class Main {
    public static void main( String[] args ) {
        Hotel hotel = new Hotel();
        HotelSystem hotelSystem = new HotelSystem();
        hotelSystem.manageHotel(hotel);
    }
}
