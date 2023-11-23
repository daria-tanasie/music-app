package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;
import java.util.Objects;

public final class Load extends CommandsInput {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final boolean loaded;

    public Load(final boolean loaded) {
    this.loaded = loaded;
    }

    /**
     * method that will execute the load command
     * @param command
     * @param commandsOutputs
     */

    public void execute(final CommandsInput command,
                        final ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();

        if (size == 0) {
            setCommand(command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }

        if (loaded) {
            setCommand(command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }

        CommandsOutput lastComm = commandsOutputs.get(size - 1);

        if (!Objects.equals(lastComm.getCommand(), "select")) {
            setCommand(command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }

        setCommand(command);
        currentCommand.setMessage("Playback loaded successfully.");
        commandsOutputs.add(currentCommand);
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public void setCommand(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
