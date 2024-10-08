package main.spotify.actions.playlist_comm;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

@Getter @Setter
public final class Playlists {
    private String name;
    private String owner;
    private int id;
    public ArrayList<Songs> songs;
    private String visibility = "public";
    private int followers = 0;
    CommandsOutput currentCommand = new CommandsOutput();

    public Playlists(final String name, final String owner) {
        this.name = name;
        this.owner = owner;
    }

    public Playlists() {
    }

    /**
     * method that will show the playlists of a user
     * @param users
     * @param command
     * @param commandsOutputs
     */

    public void showPlaylists(final ArrayList<Users> users, final CommandsInput command,
                              final ArrayList<CommandsOutput> commandsOutputs) {

        Users user = new Users();

        for (Users iter : users) {
            if (iter.getUsername().equals(command.getUsername())) {
                user = iter;
                break;
            }
        }

        set(command);

        for (Playlists playlist : user.playlists) {
            CommandsOutput.Details details = new CommandsOutput.Details();
            details.songs = new ArrayList<>();
            details.setName(playlist.getName());


            for (int i = 0; i < playlist.songs.size(); i++) {
                details.songs.add(playlist.songs.get(i).getName());
            }

            details.setVisibility(playlist.getVisibility());
            details.setFollowers(playlist.getFollowers());

            if (currentCommand.result == null) {
                currentCommand.result = new ArrayList<>();
            }
            currentCommand.result.add(details);
        }
        commandsOutputs.add(currentCommand);
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
