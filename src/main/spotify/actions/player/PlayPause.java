package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

public final class PlayPause extends CommandsInput {
    private final CommandsOutput currentCommand = new CommandsOutput();

    public PlayPause() {
    }

    public void execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs,
                        boolean paused, final boolean loaded) {
        if (!loaded) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please load a source before attempting " +
                    "to pause or resume playback.");
        }

        setCommand(currentCommand, command);
        if (paused) {
            currentCommand.setMessage("Playback resumed successfully.");
        } else {
            currentCommand.setMessage("Playback paused successfully.");
        }
        commandsOutputs.add(currentCommand);
    }

    public void setCommand(final CommandsOutput currentCommand, final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
