package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

public class AddRemoveInPlaylist {


    private final CommandsOutput currentCommand = new CommandsOutput();


    public void execute(CommandsInput command, boolean loaded, ArrayList<CommandsOutput> commandsOutputs,
                        String currentAudio, ArrayList<Users> users, ArrayList<Songs> songs) {

        set(command, currentCommand);

        if(!loaded) {
            currentCommand.setMessage("Please load a source before adding to or removing from the playlist.");
            commandsOutputs.add(currentCommand);
            return;
        }

        Users user = new Users();

        for(Users iter : users) {
            if(iter.getUsername().equals(command.getUsername())) {
                user = iter;
            }
        }

        if(user.playlists == null || command.getItemNumber() > user.playlists.size()) {
            currentCommand.setMessage("The specified playlist does not exist.");
            commandsOutputs.add(currentCommand);
            return;
        }

        for(Songs song : songs) {
            if(song.getName().equals(currentAudio)) {
                if(user.playlists.get(command.getItemNumber()).songs != null)
                    for(Songs curr : user.playlists.get(command.getItemNumber()).songs) {
                        if(curr.getName().equals(currentAudio)) {
                            user.playlists.get(command.getItemNumber()).songs.remove(curr);
                            currentCommand.setMessage("Successfully removed from playlist.");
                            commandsOutputs.add(currentCommand);
                            return;
                        }
                    }
                if(user.playlists.get(command.getItemNumber()).songs == null)
                    user.playlists.get(command.getItemNumber()).songs = new ArrayList<>();
                user.playlists.get(command.getItemNumber()).songs.add(song);
                currentCommand.setMessage("Successfully added to playlist.");
                commandsOutputs.add(currentCommand);
                return;
            }
        }

        currentCommand.setMessage("The loaded source is not a song.");
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsInput command, CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }

}
