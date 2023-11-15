package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;
import java.util.Objects;

public class PlayPause {
    private ArrayList<CommandsOutput> commandsOutputs;
    private CommandsOutput currentCommand = new CommandsOutput();

    public PlayPause() {}

    public void execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs,
                                                    boolean paused, boolean loaded) {
        if(!loaded) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please load a source before attempting to pause or resume playback.");
        }

        setCommand(currentCommand, command);
        if(paused) {
            currentCommand.setMessage("Playback resumed successfully.");
        } else {
            currentCommand.setMessage("Playback paused successfully.");
        }
        commandsOutputs.add(currentCommand);
    }

    public void setCommand(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
