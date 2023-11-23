package main.spotify.actions.playlist_comm;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Users;

import java.util.ArrayList;

public final class Follow {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<Users> users;
    private final ArrayList<CommandsOutput> commandsOutputs;

    public Follow(final ArrayList<Users> users,
                  final ArrayList<CommandsOutput> commandsOutputs) {
        this.users = users;
        this.commandsOutputs = commandsOutputs;
    }

    /**
     * executes the follow command
     * @param command
     * @param selectedPlaylist
     * @param playlists
     * @param currentAudio
     */

    public void execute(final CommandsInput command, final String selectedPlaylist,
                        final ArrayList<Playlists> playlists, final String currentAudio) {

        set(command);

        if (currentAudio == null || !commandsOutputs.get(commandsOutputs.size() - 1)
                                .getCommand().equals("select")) {
            currentCommand.setMessage("Please select a source before following or unfollowing.");
            commandsOutputs.add(currentCommand);
            return;
        }

        Users currentUser = new Users();

        for (Users user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                currentUser = user;
            }
        }

        for (Playlists playlist : playlists) {
            if (playlist.getName().equals(selectedPlaylist)) {
                if (playlist.getOwner().equals(command.getUsername())) {
                    currentCommand.setMessage("You cannot follow or unfollow your own playlist.");
                    commandsOutputs.add(currentCommand);
                    return;
                }
                if (currentUser.following != null) {
                    for (Playlists playlists1 : currentUser.following) {
                        if (playlists1.getName().equals(playlist.getName())) {
                            currentUser.following.remove(playlists1);
                            playlist.setFollowers(playlist.getFollowers() - 1);
                            currentCommand.setMessage("Playlist unfollowed successfully.");
                            commandsOutputs.add(currentCommand);
                            return;
                        }
                    }
                } else {
                    currentUser.following = new ArrayList<>();
                }
                currentUser.following.add(playlist);
                playlist.setFollowers(playlist.getFollowers() + 1);
                currentCommand.setMessage("Playlist followed successfully.");
                commandsOutputs.add(currentCommand);
                return;
            }
        }

        currentCommand.setMessage("The selected source is not a playlist.");
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
