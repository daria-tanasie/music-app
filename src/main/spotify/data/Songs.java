package main.spotify.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public final class Songs {
    private String name;
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private int likes;

    public Songs() {
    }
}

