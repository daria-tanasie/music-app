package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

public final class LikeUnlike {
    private final CommandsOutput currentCommand = new CommandsOutput();

    /**
     * method that executes the like/unlike command
     * @param command
     * @param loaded
     * @param commandsOutputs
     * @param currentAudio
     * @param users
     * @param songs
     */

    public void execute(final CommandsInput command, final boolean loaded,
                        final ArrayList<CommandsOutput> commandsOutputs,
                        final String currentAudio, final ArrayList<Users> users,
                        final ArrayList<Songs> songs) {

        set(command);

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

        if (user.likedSongs == null) {
            user.likedSongs = new ArrayList<>();
        }

        for (Songs song : songs) {
            if (song.getName().equals(currentAudio)) {
                for (int i = 0; i < user.likedSongs.size(); i++) {
                    if (user.likedSongs.get(i).equals(song)) {
                        user.likedSongs.remove(i);
                        song.setLikes(song.getLikes() - 1);
                        currentCommand.setMessage("Unlike registered successfully.");
                        commandsOutputs.add(currentCommand);
                        return;
                    }
                }

                user.likedSongs.add(song);
                song.setLikes(song.getLikes() + 1);
                currentCommand.setMessage("Like registered successfully.");
                commandsOutputs.add(currentCommand);
                return;
            }
        }

        currentCommand.setMessage("Loaded source is not a song.");
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
