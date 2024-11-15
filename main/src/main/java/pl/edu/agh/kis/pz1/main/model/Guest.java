package pl.edu.agh.kis.pz1.main.model;

import lombok.*;

/**
 * Represents a guest in the hotel system.
 * Contains the guest's personal information such as name and surname.
 */
@Data
public class Guest {
    private String name;
    private String surname;

    /**
     * Constructs a new Guest object with the specified name and surname.
     *
     * @param name    the name of the guest
     * @param surname the surname of the guest
     */
    public Guest(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getInfo(){ return name + " " + surname; }
}
