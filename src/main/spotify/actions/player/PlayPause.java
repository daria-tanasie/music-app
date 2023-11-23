package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

public final class PlayPause extends CommandsInput {
    private final CommandsOutput currentCommand = new CommandsOutput();

    public PlayPause() {
    }

    /**
     * method that will execute play/pause command
     * @param command
     * @param commandsOutputs
     * @param paused
     * @param loaded
     */

    public void execute(final CommandsInput command,
                        final ArrayList<CommandsOutput> commandsOutputs,
                        final boolean paused, final boolean loaded) {
        if (!loaded) {
            setCommand(command);
            currentCommand.setMessage("Please load a source before attempting "
                    + "to pause or resume playback.");
        }

        setCommand(command);
        if (paused) {
            currentCommand.setMessage("Playback resumed successfully.");
        } else {
            currentCommand.setMessage("Playback paused successfully.");
        }
        commandsOutputs.add(currentCommand);
    }

    /**
     * method that sets the output for the current command
     * @param command
     */

    public void setCommand(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
