package main.spotify.commands;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommandsInput {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Filters filters;
    private int itemNumber;
    private String playlistName;
    private int playlistId;
    private int seed;

    @Getter @Setter
    public class Filters{
        private List<String> tags;
        private String lyrics;
        private String owner;
        private String name;
        private String artist;
        private String album;
        private String releaseYear;
        private String genre;
    }

//    @Getter @Setter
//    public class Select extends Commands {
//        private int itemNumber;
//    }

}
