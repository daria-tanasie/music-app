package main.spotify.data;

import fileio.input.LibraryInput;
import main.spotify.data.Songs;
import main.spotify.data.Podcasts;
import main.spotify.data.Users;

import java.util.ArrayList;

public final class Library {
    private ArrayList<Songs> songs;
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Users> users;

    public Library() {
    }

    public ArrayList<Songs> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<Songs> songs) {
        this.songs = songs;
    }

    public ArrayList<Podcasts> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final ArrayList<Podcasts> podcasts) {
        this.podcasts = podcasts;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<Users> users) {
        this.users = users;
    }
}