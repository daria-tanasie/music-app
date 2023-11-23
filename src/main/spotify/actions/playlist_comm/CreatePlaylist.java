package main.spotify.actions.playlist_comm;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Users;

import java.util.ArrayList;

@Getter @Setter
public final class CreatePlaylist extends CommandsInput {

    private String username;
    private String playlistName;

    private CommandsOutput currentCommand = new CommandsOutput();

    public CreatePlaylist() {
    }

    /**
     * method that executes the createPlaylist command
     * @param command
     * @param commandsOutputs
     * @param users
     * @return
     */

    public boolean execute(final CommandsInput command,
                           final ArrayList<CommandsOutput> commandsOutputs,
                           final ArrayList<Users> users) {
        boolean ok = true;
        int userPos = 0;
        setCommand(command);
        for (Users user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                if (user.playlists != null) {
                    for (Playlists playlistsIter : user.playlists) {
                        if (playlistsIter.getName().equals(command.getPlaylistName())) {
                            ok = false;
                            break;
                        }
                    }
                }
                break;
            }
            userPos++;
        }
        if (ok) {
            if (users.get(userPos).playlists == null) {
                users.get(userPos).playlists = new ArrayList<>();
            }
            currentCommand.setMessage("Playlist created successfully.");
        } else {
            currentCommand.setMessage("A playlist with the same name already exists.");
        }
        commandsOutputs.add(currentCommand);
        return ok;
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public void setCommand(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }

    /**
     * creates a playlist with the given name
     * @param command
     * @return
     */

    public Playlists create(final CommandsInput command) {
        return new Playlists(command.getPlaylistName(), command.getUsername());
    }

}
