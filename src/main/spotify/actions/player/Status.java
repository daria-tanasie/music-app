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
    final ArrayList<CommandsOutput> commandsOutputs;

    public Status(Library library, ArrayList<CommandsOutput> commandsOutputs, boolean loaded) {
        this.library = library;
        this.commandsOutputs = commandsOutputs;
        this.loaded = loaded;
    }

    public String execute(final int time, int lastTime, CommandsInput command, boolean paused,
                          boolean shuffle, String repeat, String currentAudio,
                          String selectedPodcast, String selectedPlaylist,
                          ArrayList<Playlists> playlists) {

        int duration = 0;
        int pos = 0;
        if (currentAudio == null) {
            set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
            commandsOutputs.add(currentCommand);
            return repeat;
        }

        if (!loaded && selectedPlaylist != null) {
            set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
            commandsOutputs.add(currentCommand);
            return repeat;
        }
        if (isSong(currentAudio, library) && selectedPlaylist == null) {
            ArrayList<Songs> songs = library.getSongs();
            for (Songs song : songs) {
                if (Objects.equals(song.getName(), currentAudio)) {
                    duration = song.getDuration() - time;
                    if(repeat.equals("Repeat Once") && duration <= 0) {
                        duration = duration + song.getDuration();
                        repeat = "No Repeat";
                    }
                    if (repeat.equals("Repeat Infinite") && duration <= 0) {
                        while(duration <= 0)
                            duration = duration + song.getDuration();
                    }
                    set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
                    break;
                }
            }
        } else if (selectedPodcast != null && isPodcast(selectedPodcast, library)
                    && selectedPlaylist == null){
            ArrayList<Podcasts> podcasts = library.getPodcasts();
            for(Podcasts podcast : podcasts) {
                if (selectedPodcast.equals(podcast.getName())) {
                    for (Episodes episode : podcast.getEpisodes()) {
                        if (episode.getName().equals(currentAudio)) {
                            duration = episode.getDuration() - time;
                            if (duration <= 0 && podcast.getEpisodes().get(pos + 1) != null) {
                                currentAudio = podcast.getEpisodes().get(pos + 1).getName();
                                duration = podcast.getEpisodes().get(pos + 1).getDuration() + duration;
                            }
                            set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
                            break;
                        }
                        pos++;
                    }
                }
            }
        } else {
            for (Playlists playlist : playlists) {
                if (playlist.getName().equals(selectedPlaylist) && playlist.songs != null) {
                    for(Songs song : playlist.songs) {
                        if (song.getName().equals(currentAudio)) {
                            duration = song.getDuration() - time;
                            if (duration <= 0 && repeat.equals("Repeat Current Song")) {
                                duration = duration + song.getDuration();
                                set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
                                break;
                            }
                            if (repeat.equals("Repeat Current Song")) {
                                duration = lastTime - time;
                                set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
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
                                set(currentCommand, command, paused, shuffle,
                                        repeat, currentAudio, duration);
                                break;
                            }
                            if (duration <= 0 && playlist.songs.get(pos + 1) != null) {
                                currentAudio = playlist.songs.get(pos + 1).getName();
                                duration = playlist.songs.get(pos + 1).getDuration() + duration;
                            }

                            set(currentCommand, command, paused, shuffle,
                                    repeat, currentAudio, duration);
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

    public void set(CommandsOutput currentCommand, CommandsInput command, boolean paused,
                                boolean shuffle, String repeat, String currentAudio, int remaining) {
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

    public boolean isSong(String currentAudio, Library library) {
        boolean isSong = false;
        for (Songs song : library.getSongs()) {
            if (currentAudio.equals(song.getName())) {
                isSong = true;
                break;
            }
        }
        return isSong;
    }

    public boolean isPodcast(String currentAudio, Library library) {
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

