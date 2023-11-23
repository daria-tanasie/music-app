package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Users;

import java.util.ArrayList;

public final class SwitchVisibility {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<Users> users;
    private final ArrayList<CommandsOutput> commandsOutputs;

    public SwitchVisibility(final ArrayList<Users> users,
                  final ArrayList<CommandsOutput> commandsOutputs) {
        this.users = users;
        this.commandsOutputs = commandsOutputs;
    }

    /**
     * method that executes the switchVisibility command
     * @param command
     */

    public void execute(final CommandsInput command) {

        Users currentUser = new Users();
        int pos = 0;
        set(command);

        for (Users user : users) {
            if (user.getUsername().equals(command.getUsername())) {
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

    /**
     * sets the output for the current command
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }
}
