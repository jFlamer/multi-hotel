package pl.edu.agh.kis.pz1.main.commands;

import pl.edu.agh.kis.pz1.main.model.Hotel;

/**
 * The {@code Exit} class handles the process of terminating the hotel management system.
 * When executed, this command will terminate the application by invoking {@link System#exit(int)}.
 * It is typically used when the user wishes to exit the hotel system gracefully.
 * <p>
 * This class does not interact with the hotel or modify any data but simply ends the program execution.
 * </p>
 */
public class Exit extends Command {

    /**
     * Executes the exit command, which terminates the application.
     * This method does not perform any operations on the {@link Hotel} object as the exit command
     * is intended solely to stop the program.
     * <p>
     * When this command is executed, it calls {@link System#exit(int)} with a status code of 0,
     * which will terminate the JVM and end the execution of the program.
     * </p>
     *
     * @param hotel The {@link Hotel} object, which is not used in this command.
     */
    @Override
    public void execute(Hotel hotel) {
        System.exit(0);
    }
}
