package main.spotify.search_bar;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

@Getter @Setter
public class Select {
    //sprivate ArrayList<CommandsOutput> commandsOutput = new CommandsOutput();
    private ArrayList<CommandsOutput> commandsOutputs;
    private CommandsOutput currentCommand = new CommandsOutput();

    public Select() {
        //this.commandsOutputs = commandsOutputs;
    }

    public void execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();

        CommandsOutput lastComm = commandsOutputs.get(size - 1);

        if(lastComm.results.isEmpty())
            return;

        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());

        if(lastComm.results.size() > command.getItemNumber()) {
            int pos = command.getItemNumber() - 1;
            String selectedSong = lastComm.results.get(pos);
            currentCommand.setMessage("Successfully selected " + selectedSong + ".");
        }

        if(lastComm.results.size() < command.getItemNumber()) {
            currentCommand.setMessage("The selected ID is too high.");
        }

        commandsOutputs.add(currentCommand);
    }
}
