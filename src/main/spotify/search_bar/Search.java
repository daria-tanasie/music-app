package main.spotify.search_bar;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Podcasts;
import main.spotify.data.Songs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Getter @Setter
public class Search {
    private CommandsInput.Filters filters;
    private ArrayList<Songs> songs;
    private ArrayList<Podcasts> podcasts;
    private CommandsInput commands;
    private CommandsOutput commandsOutput = new CommandsOutput();
    ArrayNode output;

    public Search(ArrayList<Songs> songs, ArrayList<Podcasts> podcasts) {
        this.songs = songs;
        this.podcasts = podcasts;
    }
    public void execute(CommandsInput command, CommandsInput.Filters filters) {
        commandsOutput.results = new ArrayList<>();
        if(Objects.equals(command.getType(), "song")) {
            for (Songs song : songs) {
                if (filters.getName() != null && song.getName().startsWith(filters.getName())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if (filters.getAlbum() != null && Objects.equals(song.getAlbum(), filters.getAlbum())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if (filters.getArtist() != null && Objects.equals(song.getArtist(), filters.getArtist())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if (filters.getGenre() != null && Objects.equals(song.getGenre(), filters.getGenre())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if (filters.getLyrics() != null && song.getLyrics().contains(filters.getLyrics())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if(filters.getTags() != null && song.getTags().containsAll(filters.getTags())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(song.getName());
                }

                if(filters.getReleaseYear() != null) {
                    String operator = filters.getReleaseYear().substring(0,1);
                    boolean ok = false;

                    switch (operator) {
                        case ">" -> {
                            if(song.getReleaseYear() > Integer.parseInt(filters.getReleaseYear().substring(1))) {
                                ok = true;
                            }
                        }
                        case "<" -> {
                            if(song.getReleaseYear() < Integer.parseInt(filters.getReleaseYear().substring(1))) {
                                ok = true;
                            }
                        }
                        default -> {
                            return;
                        }
                    }
                    if(ok) {
                        commandsOutput.setCommand(command.getCommand());
                        commandsOutput.setTimestamp(command.getTimestamp());
                        commandsOutput.setUsername(command.getUsername());
                        commandsOutput.results.add(song.getName());
                    }
                }

            }
        }

        if(Objects.equals(command.getType(), "podcasts")) {
            for (Podcasts podcast : podcasts) {
                if (filters.getName() != null && podcast.getName().startsWith(filters.getName())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(podcast.getName());
                }
                if (filters.getOwner() != null && Objects.equals(podcast.getOwner(), filters.getOwner())) {
                    commandsOutput.setCommand(command.getCommand());
                    commandsOutput.setTimestamp(command.getTimestamp());
                    commandsOutput.setUsername(command.getUsername());
                    commandsOutput.results.add(podcast.getOwner());
                }
            }
        }
        commandsOutput.setMessage("Search returned " + commandsOutput.results.size() + " results");
        //output.add( comm);
        System.out.println(commandsOutput.getMessage());
        System.out.println(commandsOutput.getResults());
    }
}
