package main.spotify.data;

import lombok.Getter;
import java.util.ArrayList;

@Getter
public final class Podcasts {
    private String name;
    private String owner;
    private ArrayList<Episodes> episodes;

    public Podcasts() {
    }
}
