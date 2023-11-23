package main.spotify.data;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Library {
    private ArrayList<Songs> songs;
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Users> users;

    public Library() {
    }
}
