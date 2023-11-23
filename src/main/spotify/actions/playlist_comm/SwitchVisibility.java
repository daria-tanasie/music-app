package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Users;

import java.util.ArrayList;

public class SwitchVisibility {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<Users> users;
    private final ArrayList<CommandsOutput> commandsOutputs;

    public SwitchVisibility(ArrayList<Users> users,
                  ArrayList<CommandsOutput> commandsOutputs) {
        this.users = users;
        this.commandsOutputs = commandsOutputs;
    }

    public void execute(CommandsInput command) {

        Users currentUser = new Users();
        int pos = 0;
        set(currentCommand, command);

        for (Users user : users) {
            if(user.getUsername().equals(command.getUsername())) {
                currentUser = user;
            }
        }

        if (currentUser.playlists == null) {
            return;
        }

        if (currentUser.playlists.size() < command.getPlaylistId()) {
            currentCommand.setMessage("The specified playlist ID is too high.");
            commandsOutputs.add(currentCommand);
            return;
        }

        for (Playlists playlist : currentUser.playlists) {
            if (pos == command.getPlaylistId() - 1) {
                if (playlist.getVisibility().equals("private")) {
                    playlist.setVisibility("public");
                    currentCommand.setMessage("Visibility status updated successfully to public.");
                } else {
                    playlist.setVisibility("private");
                    currentCommand.setMessage("Visibility status updated successfully to private.");
                }
            }
            pos++;
        }
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }
}
