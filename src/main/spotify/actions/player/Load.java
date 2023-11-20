package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;
import java.util.Objects;

public class Load extends CommandsInput{
    private final CommandsOutput currentCommand = new CommandsOutput();

    public Load() {}

    public void execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();

        if(size == 0) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            return;
        }

        CommandsOutput lastComm = commandsOutputs.get(size - 1);

        if(!Objects.equals(lastComm.getCommand(), "select")) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please select a source before attempting to load.");
            return;
        }
//         TODO "You can't load an empty audio collection!"
//        if(lastComm.results.isEmpty())
//            return;

        setCommand(currentCommand, command);
        currentCommand.setMessage("Playback loaded successfully.");
        commandsOutputs.add(currentCommand);
    }

    public void setCommand(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
