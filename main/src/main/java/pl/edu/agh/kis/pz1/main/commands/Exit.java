package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;

public class Exit extends Command {
    @Override
    public void execute(Hotel hotel) {
        System.exit(0);
    }
}
