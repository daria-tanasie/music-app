package main.spotify.actions.playlist_comm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class PrefSongs {
    private String command;
    private String user;
    private int timestamp;
    private ArrayList<String> result;
}
