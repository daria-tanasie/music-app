package main.spotify.actions.trending;

import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;

import java.util.ArrayList;

public final class GetTop5 {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<CommandsOutput> commandsOutputs;
    private final int five = 5;

    public GetTop5(final ArrayList<CommandsOutput> commandsOutputs) {
        this.commandsOutputs = commandsOutputs;
    }

    /**
     * methods that gets the top 5 playlists
     * @param command
     * @param playlists
     */

    public void executeP(final CommandsInput command, final ArrayList<Playlists> playlists) {
        currentCommand.result = new ArrayList<>();
        ArrayList<Playlists> sortedPlaylists;
        sortedPlaylists = playlists;
        Playlists aux;

        int size = playlists.size();
        if (size > five) {
            size = five;
        }

        for (int i = 0; i < sortedPlaylists.size(); i++) {
            for (int j = 0; j < sortedPlaylists.size(); j++) {
                if (sortedPlaylists.get(i).getFollowers()
                        > sortedPlaylists.get(j).getFollowers()) {
                    aux = sortedPlaylists.get(i);
                    sortedPlaylists.set(i, sortedPlaylists.get(j));
                    sortedPlaylists.set(j, aux);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            currentCommand.result.add(sortedPlaylists.get(i).getName());
        }
        set(command);
        commandsOutputs.add(currentCommand);
    }

    /**
     * method that gets the 5 most liked songs
     * @param command
     * @param songs
     */

    public void executeS(final CommandsInput command, final ArrayList<Songs> songs) {
        currentCommand.result = new ArrayList<>();
        ArrayList<Songs> sortedSongs = new ArrayList<>();
        Songs aux;

        for (Songs song : songs) {
            if (song.getLikes() != 0) {
                sortedSongs.add(song);
            }
        }

        for (int i = 0; i < sortedSongs.size() - 1; i++) {
            for (int j = 1; j < sortedSongs.size(); j++) {
                if (sortedSongs.get(i).getLikes() < sortedSongs.get(j).getLikes()) {
                    aux = sortedSongs.get(i);
                    sortedSongs.set(i, sortedSongs.get(j));
                    sortedSongs.set(j, aux);
                }
            }
        }

        if (sortedSongs.size() < five) {
            int pos = 0;
            while (sortedSongs.size() != five) {
                if (!sortedSongs.contains(songs.get(pos))) {
                    sortedSongs.add(songs.get(pos));
                }
                pos++;
            }
        }

        for (int i = 0; i < five; i++) {
            currentCommand.result.add(sortedSongs.get(i).getName());
        }
        set(command);
        commandsOutputs.add(currentCommand);
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
    }
}
