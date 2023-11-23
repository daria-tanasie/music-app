package main.spotify.actions.player;

import lombok.Getter;
import lombok.Setter;
import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Episodes;
import main.spotify.data.Library;
import main.spotify.data.Podcasts;
import main.spotify.data.Songs;

import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public final class Status extends CommandsInput {

    private CommandsOutput currentCommand = new CommandsOutput();
    private final Library library;
    private final boolean loaded;
    private final ArrayList<CommandsOutput> commandsOutputs;

    public Status(final Library library, final ArrayList<CommandsOutput> commandsOutputs,
                  final boolean loaded) {
        this.library = library;
        this.commandsOutputs = commandsOutputs;
        this.loaded = loaded;
    }

    /**
     * method that will execute the status command
     * @param time
     * @param lastTime
     * @param command
     * @param paused
     * @param shuffle
     * @param repeat
     * @param currentAudio
     * @param selectedPodcast
     * @param selectedPlaylist
     * @param playlists
     * @return
     */

    public String execute(final int time, final int lastTime, final CommandsInput command,
                          final boolean paused, final boolean shuffle,
                          String repeat, String currentAudio,
                          final String selectedPodcast, final String selectedPlaylist,
                          final ArrayList<Playlists> playlists) {

        int duration = 0;
        int pos = 0;
        if (currentAudio == null) {
            set(command, paused, shuffle, repeat, currentAudio, duration);
            commandsOutputs.add(currentCommand);
            return repeat;
        }

        if (!loaded && selectedPlaylist != null) {
            set(command, paused, shuffle, repeat, currentAudio, duration);
            commandsOutputs.add(currentCommand);
            return repeat;
        }
        if (isSong(currentAudio) && selectedPlaylist == null) {
            ArrayList<Songs> songs = library.getSongs();
            for (Songs song : songs) {
                if (Objects.equals(song.getName(), currentAudio)) {
                    duration = song.getDuration() - time;
                    if (repeat.equals("Repeat Once") && duration <= 0) {
                        duration = duration + song.getDuration();
                        repeat = "No Repeat";
                    }
                    if (repeat.equals("Repeat Infinite") && duration <= 0) {
                        while (duration <= 0) {
                            duration = duration + song.getDuration();
                        }
                    }
                    set(command, paused, shuffle, repeat, currentAudio, duration);
                    break;
                }
            }
        } else if (selectedPodcast != null && isPodcast(selectedPodcast)
                    && selectedPlaylist == null) {
            ArrayList<Podcasts> podcasts = library.getPodcasts();
            for (Podcasts podcast : podcasts) {
                if (selectedPodcast.equals(podcast.getName())) {
                    for (Episodes episode : podcast.getEpisodes()) {
                        if (episode.getName().equals(currentAudio)) {
                            duration = episode.getDuration() - time;
                            if (duration <= 0 && podcast.getEpisodes().get(pos + 1) != null) {
                                currentAudio = podcast.getEpisodes().get(pos + 1).getName();
                                duration = podcast.getEpisodes().get(pos + 1).getDuration()
                                        + duration;
                            }
                            set(command, paused, shuffle, repeat, currentAudio, duration);
                            break;
                        }
                        pos++;
                    }
                }
            }
        } else {
            for (Playlists playlist : playlists) {
                if (playlist.getName().equals(selectedPlaylist) && playlist.songs != null) {
                    for (Songs song : playlist.songs) {
                        if (song.getName().equals(currentAudio)) {
                            duration = song.getDuration() - time;
                            if (duration <= 0 && repeat.equals("Repeat Current Song")) {
                                duration = duration + song.getDuration();
                                set(command, paused, shuffle, repeat, currentAudio, duration);
                                break;
                            }
                            if (repeat.equals("Repeat Current Song")) {
                                duration = lastTime - time;
                                set(command, paused, shuffle, repeat, currentAudio, duration);
                                break;
                            }

                            if (duration <= 0 && repeat.equals("Repeat All")) {
                                int iter = pos + 1;
                                while (duration <= 0) {
                                    currentAudio = playlist.songs.get(iter).getName();
                                    duration = playlist.songs.get(iter).getDuration() + duration;
                                    iter++;
                                    if (iter >= playlist.songs.size()) {
                                        iter = 0;
                                    }
                                }
                                set(command, paused, shuffle, repeat, currentAudio, duration);
                                break;
                            }
                            if (duration <= 0 && playlist.songs.get(pos + 1) != null) {
                                currentAudio = playlist.songs.get(pos + 1).getName();
                                duration = playlist.songs.get(pos + 1).getDuration() + duration;
                            }

                            set(command, paused, shuffle, repeat, currentAudio, duration);
                            break;
                        }
                        pos++;
                    }
                }
            }
        }
        commandsOutputs.add(currentCommand);
        return repeat;
    }

    /**
     * sets the output for status command
     * @param command
     * @param paused
     * @param shuffle
     * @param repeat
     * @param currentAudio
     * @param remaining
     */

    public void set(final CommandsInput command, final boolean paused, final boolean shuffle,
                    final String repeat, final String currentAudio, int remaining) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
        CommandsOutput.Stats stats = new CommandsOutput.Stats();
        stats.setName(currentAudio);
        stats.setShuffle(shuffle);
        stats.setRepeat(repeat);
        stats.setPaused(paused);
        if (remaining <= 0) {
            remaining = 0;
            stats.setName("");
            stats.setPaused(true);
            stats.setRepeat("No Repeat");
        }
        stats.setRemainedTime(remaining);
        currentCommand.setStats(stats);
    }

    /**
     * verifies if currentAudio is a song
     * @param currentAudio
     * @return
     */

    public boolean isSong(final String currentAudio) {
        boolean isSong = false;
        for (Songs song : library.getSongs()) {
            if (currentAudio.equals(song.getName())) {
                isSong = true;
                break;
            }
        }
        return isSong;
    }

    /**
     * verifies if currentAudio is a podcast
     * @param currentAudio
     * @return
     */

    public boolean isPodcast(final String currentAudio) {
        boolean isPodcast = false;
        for (Podcasts podcast : library.getPodcasts()) {
            if (currentAudio.equals(podcast.getName())) {
                isPodcast = true;
                break;
            }
        }
        return isPodcast;
    }
}

