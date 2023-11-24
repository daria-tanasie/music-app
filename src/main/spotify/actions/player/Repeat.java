package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

public final class Repeat {

    private final CommandsOutput currentCommand = new CommandsOutput();
    private final int three = 3;

    public Repeat() {
    }

    /**
     * method that will execute the repeat command
     * @param command
     * @param commandsOutputs
     * @param loaded
     * @param repeatCode
     * @param selectedPlaylist
     * @return
     */

    public int execute(final CommandsInput command,
                       final ArrayList<CommandsOutput> commandsOutputs,
                       final boolean loaded, int repeatCode,
                       final String selectedPlaylist) {
        if (!loaded) {
            set(command);
            currentCommand.setMessage("Please load a source before setting the repeat status.");
            commandsOutputs.add(currentCommand);
            return repeatCode;
        }

        String repeat = null;
        repeatCode++;
        if (repeatCode == three) {
            repeatCode = 0;
        }

        switch (repeatCode) {
            case 0 -> {
                repeat = "no repeat";
            }
            case 1 -> {
                if (selectedPlaylist != null) {
                    repeat = "repeat all";
                } else {
                    repeat = "repeat once";
                }
            }
            case 2 -> {
                if (selectedPlaylist != null) {
                    repeat = "repeat current song";
                } else {
                    repeat = "repeat infinite";
                }
            }
            default -> {

            }
        }
        set(command);
        currentCommand.setMessage("Repeat mode changed to " + repeat + ".");
        commandsOutputs.add(currentCommand);
        return  repeatCode;
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public  void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }

}
