package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;

public abstract class Command {
    public abstract void execute(Hotel hotel);
}
