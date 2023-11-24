package main.spotify.actions.player;

import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Podcasts;

import java.util.ArrayList;

public final class NextPrev {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<CommandsOutput> commandsOutputs;
    private final ArrayList<Podcasts> podcasts;
    final ArrayList<Playlists> playlists;

    public NextPrev(final ArrayList<CommandsOutput> commandsOutputs,
                    final ArrayList<Podcasts> podcasts,
                    final ArrayList<Playlists> playlists) {
        this.commandsOutputs = commandsOutputs;
        this.podcasts = podcasts;
        this.playlists = playlists;
    }

    /**
     * method that goes to prev audio
     * @param command
     * @param selectedPlaylist
     * @param currentAudio
     * @param time
     * @return
     */

    public String executePrev(final CommandsInput command, final String selectedPlaylist,
                              String currentAudio, int time) {

        set(command);

        if (selectedPlaylist == null) {
            currentCommand.setMessage("Please load a source before returning to the "
                    + "previous track.");
            commandsOutputs.add(currentCommand);
            return currentAudio;
        }

        for (Playlists playlist : playlists) {
            if (selectedPlaylist.equals(playlist.getName())) {
                for (int i = 0; i < playlist.songs.size(); i++) {
                    if (playlist.songs.get(i).getName().equals(currentAudio)) {
                        if (i - 1 < 0 || playlist.songs.get(i).getDuration() - time > 1
                                && time != 0) {
                           break;
                        }
                        currentAudio = playlist.songs.get(i - 1).getName();
                        break;
                    }
                }
            }
        }

        currentCommand.setMessage("Returned to previous track successfully. The current track is "
                + currentAudio + ".");
        commandsOutputs.add(currentCommand);
        return currentAudio;
    }

    /**
     * method that goes to next audio
     * @param command
     * @param selectedPlaylist
     * @param currentAudio
     * @param selectedPodcast
     * @return
     */

    public String executeNext(final CommandsInput command, final String selectedPlaylist,
                              String currentAudio, final String selectedPodcast) {

        set(command);

        if (selectedPlaylist != null) {
            for (Playlists playlist : playlists) {
                if (selectedPlaylist.equals(playlist.getName())) {
                    for (int i = 0; i < playlist.songs.size(); i++) {
                        if (playlist.songs.get(i).getName().equals(currentAudio)) {
                            if (i + 1 >= playlist.songs.size()) {
                                break;
                            }
                            currentAudio = playlist.songs.get(i + 1).getName();
                            break;
                        }
                    }
                }
            }

            currentCommand.setMessage("Skipped to next track successfully. The current track is "
                    + currentAudio + ".");
            commandsOutputs.add(currentCommand);
            return currentAudio;
        } else if (selectedPodcast == null) {
            currentCommand.setMessage("Please load a source before skipping to the next track.");
            commandsOutputs.add(currentCommand);
            return currentAudio;
        }

        for (Podcasts podcast : podcasts) {
            if (selectedPodcast.equals(podcast.getName())) {
               for (int i = 0; i < podcast.getEpisodes().size(); i++) {
                   if (currentAudio.equals(podcast.getEpisodes().get(i).getName())) {
                       if (i + 1 >= podcast.getEpisodes().size()) {
                           break;
                       }
                       currentAudio = podcast.getEpisodes().get(i + 1).getName();
                       break;
                   }
               }
            }
        }
        currentCommand.setMessage("Skipped to next track successfully. The current track is "
                + currentAudio + ".");
        commandsOutputs.add(currentCommand);
        return currentAudio;
    }

    /**
     * method that sets the output
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }
}
