package main.spotify.data;

import fileio.input.SongInput;
import fileio.input.UserInput;
import fileio.input.PodcastInput;
import fileio.input.LibraryInput;
import  fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public final class Data {
    private List<SongInput> songs = new ArrayList<>();
    private List<PodcastInput> podcasts = new ArrayList<>();
    private List<LibraryInput> library = new ArrayList<>();
    private List<EpisodeInput> episodes = new ArrayList<>();
    private List<UserInput> users = new ArrayList<>();

    private static Data dataBase = null;

    private Data() {

    }
}
