package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

public class LikeUnlike {
    private final CommandsOutput currentCommand = new CommandsOutput();

    public void execute(CommandsInput command, boolean loaded, ArrayList<CommandsOutput> commandsOutputs,
                        String currentAudio, ArrayList<Users> users, ArrayList<Songs> songs) {

        set(command, currentCommand);

        if (!loaded) {
            currentCommand.setMessage("Please load a source before liking or unliking.");
            commandsOutputs.add(currentCommand);
            return;
        }

        Users user = new Users();

        for (Users iter : users) {
            if (iter.getUsername().equals(command.getUsername())) {
                user = iter;
            }
        }

        //ArrayList<Songs> likedSongs = user.likedSongs;
        if (user.likedSongs == null)
            user.likedSongs = new ArrayList<>();

        for (Songs song : songs) {
            if (song.getName().equals(currentAudio)) {
                for (int i = 0; i < user.likedSongs.size(); i++) {
                    if (user.likedSongs.get(i).equals(song)) {
                        user.likedSongs.remove(i);
                        currentCommand.setMessage("Unlike registered successfully.");
                        commandsOutputs.add(currentCommand);
                        return;
                    }
                }

                user.likedSongs.add(song);
                currentCommand.setMessage("Like registered successfully.");
                commandsOutputs.add(currentCommand);
                return;
            }
        }

        currentCommand.setMessage("Loaded source is not a song.");
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsInput command, CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }

}
