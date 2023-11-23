package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

public final class AddRemoveInPlaylist {
    private final CommandsOutput currentCommand = new CommandsOutput();

    /**
     * method that execute the add/remove command
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
            currentCommand.setMessage("Please load a source before adding "
                    + "to or removing from the playlist.");
            commandsOutputs.add(currentCommand);
            return;
        }

        Users user = new Users();

        for (Users iter : users) {
            if (iter.getUsername().equals(command.getUsername())) {
                user = iter;
            }
        }

        if (user.playlists == null || command.getPlaylistId() > user.playlists.size()) {
            currentCommand.setMessage("The specified playlist does not exist.");
            commandsOutputs.add(currentCommand);
            return;
        }

        for (Songs song : songs) {
            if (song.getName().equals(currentAudio)) {
                if (user.playlists.get(command.getPlaylistId() - 1).songs != null) {
                    for (Songs curr : user.playlists.get(command.getPlaylistId() - 1).songs) {
                        if (curr.getName().equals(currentAudio)) {
                            user.playlists.get(command.getPlaylistId() - 1).songs.remove(curr);
                            currentCommand.setMessage("Successfully removed from playlist.");
                            commandsOutputs.add(currentCommand);
                            return;
                        }
                    }
                }
                if (user.playlists.get(command.getPlaylistId() - 1).songs == null) {
                    user.playlists.get(command.getPlaylistId() - 1).songs = new ArrayList<>();
                }
                user.playlists.get(command.getPlaylistId() - 1).songs.add(song);
                currentCommand.setMessage("Successfully added to playlist.");
                commandsOutputs.add(currentCommand);
                return;
            }
        }

        currentCommand.setMessage("The loaded source is not a song.");
        commandsOutputs.add(currentCommand);
    }

    /**
     * sets the output for current command
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }

}
