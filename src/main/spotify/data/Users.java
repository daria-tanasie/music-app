package main.spotify.data;

import lombok.Getter;
import lombok.Setter;
import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

@Getter @Setter
public final class Users {
    private String username;
    private int age;
    private String city;
    public ArrayList<Playlists> playlists;
    public ArrayList<Songs> likedSongs;
    public ArrayList<Playlists> following;

    public Users() {
    }

    /**
     * shows the liked songs of the current user
     * @param command
     * @param commandsOutputs
     * @param users
     */
    public void showPrefSongs(final CommandsInput command,
                              final ArrayList<CommandsOutput> commandsOutputs,
                              final ArrayList<Users> users) {

        CommandsOutput currentCommand = new CommandsOutput();
        currentCommand.result = new ArrayList<>();
        set(command, currentCommand);
        Users user = new Users();

        for (Users iter : users) {
            if (iter.getUsername().equals(command.getUsername())) {
                user = iter;
            }
        }

        if (user.likedSongs != null) {
            for (Songs song : user.likedSongs) {
                currentCommand.result.add(song.getName());
            }
        }
        commandsOutputs.add(currentCommand);
    }

    /**
     * sets the output for the current command
     * @param command
     * @param currentCommand
     */

    public void set(final CommandsInput command, final CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}