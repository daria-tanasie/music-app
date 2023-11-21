package main.spotify.data;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

public final class Podcasts {
    private String name;
    private String owner;
    private ArrayList<Episodes> episodes;

    public Podcasts() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public ArrayList<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<Episodes> episodes) {
        this.episodes = episodes;
    }
}