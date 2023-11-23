package main.spotify.actions.search_bar;

import lombok.Getter;
import lombok.Setter;
import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Podcasts;
import main.spotify.data.Songs;

import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public final class Search extends CommandsInput {
    private CommandsInput.Filters filters;
    private ArrayList<Songs> songs;
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Playlists> playlists;
    private CommandsOutput commandsOutput = new CommandsOutput();
    private CommandsOutput currentCommand = new CommandsOutput();
    private final int five = 5;


    public Search(ArrayList<Songs> songs, ArrayList<Podcasts> podcasts,
                  ArrayList<Playlists> playlists) {
        this.songs = songs;
        this.podcasts = podcasts;
        this.playlists = playlists;
    }

    public void execute(CommandsInput command, CommandsInput.Filters filters,
                        ArrayList<CommandsOutput> output) {
        commandsOutput.results = new ArrayList<>();
        currentCommand.results = new ArrayList<>();
        int cnt = 0;
        int nrFilters;
        if (Objects.equals(command.getType(), "song")) {
            for (Songs song : songs) {
                nrFilters = nrFilters(filters);
                if (filters.getName() != null
                        && song.getName().startsWith(filters.getName()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getAlbum() != null
                        && Objects.equals(song.getAlbum(), filters.getAlbum()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getArtist() != null
                        && Objects.equals(song.getArtist(), filters.getArtist()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getTags() != null
                        && song.getTags().containsAll(filters.getTags()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getGenre() != null && Objects.equals(song.getGenre().toLowerCase(),
                        filters.getGenre().toLowerCase()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getLyrics() != null && song.getLyrics().toLowerCase()
                        .contains(filters.getLyrics().toLowerCase()) && cnt < five) {
                    nrFilters--;
                    if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                        currentCommand.results.add(song.getName());
                        cnt++;
                    }
                }

                if (filters.getReleaseYear() != null && cnt < five) {
                    String operator = filters.getReleaseYear().substring(0, 1);
                    boolean ok = false;
                    switch (operator) {
                        case ">" -> {
                            if (song.getReleaseYear() > Integer.parseInt(filters
                                    .getReleaseYear().substring(1))) {
                                ok = true;
                            }
                        }
                        case "<" -> {
                            if (song.getReleaseYear() < Integer.parseInt(filters.
                                    getReleaseYear().substring(1))) {
                                ok = true;
                            }
                        }
                        default -> {
                            return;
                        }
                    }
                    if (ok) {
                        nrFilters--;
                        if (!commandsOutput.results.contains(song.getName()) && nrFilters == 0) {
                            currentCommand.results.add(song.getName());
                            //setCommandSong(command, currentCommand);
                            cnt++;
                        }
                    }
                }
            }
            setCommandSong(command, currentCommand);
            currentCommand.setMessage("Search returned " + currentCommand.results.size()
                    + " results");
            output.add(currentCommand);
            return;
        }

        if (Objects.equals(command.getType(), "podcast")) {
            for (Podcasts podcast : podcasts) {
                if (filters.getName() != null && podcast.getName()
                        .startsWith(filters.getName()) && cnt < five) {
                    setCommandPodcast(command, commandsOutput, podcast);
                    cnt++;
                }
                if (filters.getOwner() != null && Objects.equals(podcast
                        .getOwner(), filters.getOwner()) && cnt < five) {
                    setCommandPodcast(command, commandsOutput, podcast);
                    cnt++;
                }

            }
        }

        if (Objects.equals(command.getType(), "playlist")) {
            for (Playlists playlist : playlists) {
                if (filters.getName() != null && playlist.getName().startsWith(filters.getName())
                        && cnt < five && playlist.getVisibility().equals("public")) {
                    setCommandPlaylist(command, commandsOutput, playlist);
                    cnt++;
                }
                if (filters.getOwner() != null && Objects.equals(playlist.getOwner(), filters.
                        getOwner()) && cnt < five && playlist.getVisibility().equals("public")) {
                    setCommandPlaylist(command, commandsOutput, playlist);
                    cnt++;
                }
            }
            if (commandsOutput.results.isEmpty()) {
                commandsOutput.setCommand(command.getCommand());
                commandsOutput.setUser(command.getUsername());
                commandsOutput.setTimestamp(command.getTimestamp());
            }
        }
        commandsOutput.setMessage("Search returned " + commandsOutput.results.size() + " results");
        output.add(commandsOutput);
    }

    public int nrFilters(CommandsInput.Filters filters) {
        int nr = 0;
        if (filters.getOwner() != null) {
            nr++;
        }
        if (filters.getName() != null) {
            nr++;
        }
        if (filters.getArtist() != null) {
            nr++;
        }
        if (filters.getAlbum() != null) {
            nr++;
        }
        if (filters.getGenre() != null) {
            nr++;
        }
        if (filters.getLyrics() != null) {
            nr++;
        }
        if (filters.getReleaseYear() != null) {
            nr++;
        }
        if (filters.getTags() != null) {
            nr++;
        }
        return nr;
    }

    public void setCommandSong(CommandsInput command, CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }

    public void setCommandPodcast(CommandsInput command, CommandsOutput commandsOutput,
                                  Podcasts podcast) {
        commandsOutput.setCommand(command.getCommand());
        commandsOutput.setTimestamp(command.getTimestamp());
        commandsOutput.setUser(command.getUsername());
        commandsOutput.results.add(podcast.getName());
    }

    public void setCommandPlaylist(CommandsInput command,
                                   CommandsOutput commandsOutput, Playlists playlist) {
        commandsOutput.setCommand(command.getCommand());
        commandsOutput.setTimestamp(command.getTimestamp());
        commandsOutput.setUser(command.getUsername());
        commandsOutput.results.add(playlist.getName());
    }

}
