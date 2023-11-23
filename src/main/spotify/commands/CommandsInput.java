package main.spotify.commands;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommandsInput {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private int itemNumber;
    private String playlistName;
    private int playlistId;
    private int seed;
    private Filters filters;

    @Getter @Setter
    public static class Filters {
        private ArrayList<String> tags;
        private String lyrics;
        private String owner;
        private String name;
        private String artist;
        private String album;
        private String releaseYear;
        private String genre;
    }
}
