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

    public Users() {}

    public void showPreferredSongs(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs, ArrayList<Users> users) {

        CommandsOutput currentCommand = new CommandsOutput();
        currentCommand.result = new ArrayList<>();
        set(command, currentCommand);
        Users user = new Users();

        for(Users iter : users) {
            if(iter.getUsername().equals(command.getUsername())) {
                user = iter;
            }
        }

        if(user.likedSongs != null)
            for(Songs song : user.likedSongs) {
                currentCommand.result.add(song.getName());
            }
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsInput command, CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}