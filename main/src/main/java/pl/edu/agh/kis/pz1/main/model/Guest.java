package pl.edu.agh.kis.pz1.main.model;

import lombok.*;

/**
 * Represents a guest in the hotel system.
 * Contains the guest's personal information such as name and surname.
 * The class is used to store information about individuals who stay in rooms in the hotel.
 * <p>
 * Each guest has a name and surname, and their details can be retrieved in a formatted string.
 * </p>
 */
@Data
public class Guest {
    private String name;
    private String surname;

    /**
     * Constructs a new Guest object with the specified name and surname.
     * This constructor is used to create a new guest with the given details.
     *
     * @param name    the name of the guest
     * @param surname the surname of the guest
     */
    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * Retrieves the full information of the guest as a formatted string.
     * This method returns the guest's name and surname combined into a single string.
     *
     * @return A string containing the full name of the guest in the format "name surname"
     */
    public String getInfo(){ return name + " " + surname; }
}
