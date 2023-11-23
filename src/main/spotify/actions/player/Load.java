package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;
import java.util.Objects;

public final class Load extends CommandsInput {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final boolean loaded;

    public Load(boolean loaded) {
    this.loaded = loaded;
    }

    public void execute(final CommandsInput command,
                        final ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();

        if (size == 0) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }

        if (loaded) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }

        CommandsOutput lastComm = commandsOutputs.get(size - 1);

        if (!Objects.equals(lastComm.getCommand(), "select")) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            commandsOutputs.add(currentCommand);
            return;
        }
//         TODO "You can't load an empty audio collection!"
//        if(lastComm.results.isEmpty())
//            return;

        setCommand(currentCommand, command);
        currentCommand.setMessage("Playback loaded successfully.");
        commandsOutputs.add(currentCommand);
    }

    public void setCommand(final CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
