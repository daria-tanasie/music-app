package main.spotify.actions.search_bar;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

@Getter @Setter
public final class Select extends CommandsOutput {
    private CommandsOutput currentCommand = new CommandsOutput();
    private String currentAudio;

    public Select(final String currentAudio) {
        this.currentAudio = currentAudio;
    }

    /**
     * method that will execute the select command
     * @param command
     * @param commandsOutputs
     * @return
     */

    public String execute(final CommandsInput command,
                          final ArrayList<CommandsOutput> commandsOutputs) {
        int size = commandsOutputs.size();
        String selectedSong = null;
        CommandsOutput lastComm;

        if (size == 0) {
            setCommand(command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            return null;
        }

        if (currentAudio != null) {
            setCommand(command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            commandsOutputs.add(currentCommand);
            return currentAudio;
        }


        lastComm = commandsOutputs.get(size - 1);
        if (!lastComm.getCommand().equals("search")) {
            int i = commandsOutputs.size() - 1;
            while (!commandsOutputs.get(i).getCommand().equals("search")) {
                i--;
            }
            lastComm = commandsOutputs.get(i);
        }
        if (!lastComm.getCommand().equals("search") && size > 2) {
            lastComm = commandsOutputs.get(size - 2);
        }


        if (!lastComm.getCommand().equals("search")) {
            setCommand(command);
            currentCommand.setMessage("Please conduct a search before making a selection.");
            return null;
        }

        setCommand(command);

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
