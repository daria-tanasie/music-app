package main.spotify.actions.playlist_comm;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Users;

import java.util.ArrayList;

@Getter @Setter
public class CreatePlaylist extends CommandsInput {

    private String username;
    private String playlistName;

    private CommandsOutput currentCommand = new CommandsOutput();

    public CreatePlaylist() {}

    public boolean execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs, ArrayList<Users> users) {
        boolean ok = true;
        int userPos = 0;
        setCommand(currentCommand, command);
        for (Users user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                if (user.playlists != null)
                    for (Playlists playlistsIter : user.playlists) {
                        if (playlistsIter.getName().equals(command.getPlaylistName())) {
                            ok = false;
                            break;
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
            //users.get(userPos).playlists.add(playlist);
            currentCommand.setMessage("Playlist created successfully.");
        } else {
            currentCommand.setMessage("A playlist with the same name already exists.");
        }
        commandsOutputs.add(currentCommand);
        return ok;
    }

    public void setCommand(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }

    public Playlists create(CommandsInput command) {
        return new Playlists(command.getPlaylistName(), command.getUsername());
    }

}
