package main.spotify.actions.search_bar;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;

import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public class Select extends CommandsOutput{
    private ArrayList<CommandsOutput> commandsOutputs;
    private CommandsOutput currentCommand = new CommandsOutput();
    private String currentAudio;

    public Select(String currentAudio) {
        this.currentAudio = currentAudio;
    }

    public String execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();
        boolean isSong = false;
        String selectedSong = null;
        CommandsOutput lastComm;

        if (size == 0) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            return null;
        }

        if(currentAudio != null) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            commandsOutputs.add(currentCommand);
            return currentAudio;
        }


        lastComm = commandsOutputs.get(size - 1);
        if (!lastComm.getCommand().equals("search")) {
            int i = commandsOutputs.size() - 1;
            while(!commandsOutputs.get(i).getCommand().equals("search")) {
                i--;
            }
            lastComm = commandsOutputs.get(i);
        }
        if (!lastComm.getCommand().equals("search") && size > 2) {
            lastComm = commandsOutputs.get(size - 2);
        }


        if (!lastComm.getCommand().equals("search")) {
            setCommand(currentCommand, command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            return null;
        }

//        if (lastComm.results.isEmpty())
//            return null;

        setCommand(currentCommand, command);

        if (lastComm.results.size() >= command.getItemNumber()) {
            int pos = command.getItemNumber() - 1;
            selectedSong = lastComm.results.get(pos);

            currentCommand.setMessage("Successfully selected " + selectedSong + ".");
        }

        if (lastComm.results.size() < command.getItemNumber()) {
            currentCommand.setMessage("The selected ID is too high.");
        }

        commandsOutputs.add(currentCommand);
        return selectedSong;
    }

    public void setCommand(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
