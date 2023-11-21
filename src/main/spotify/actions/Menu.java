package main.spotify.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.spotify.actions.player.Load;
import main.spotify.actions.player.PlayPause;
import main.spotify.actions.player.Status;
import main.spotify.actions.playlist_comm.*;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.*;
import main.spotify.actions.search_bar.Search;
import main.spotify.actions.search_bar.Select;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private CommandsInput[] input;
    private final Library library;
    private ArrayList<Playlists> playlists = new ArrayList<>();
    private ArrayList<Users> users = new ArrayList<>();
    private PrefSongs prefSongs = new PrefSongs();
    private final String filePathOutput;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Menu(CommandsInput[] input, String filePathOutput, Library library) {
        this.input = input;
        this.filePathOutput = filePathOutput;
        this.library = library;
    }

    public void actionsSpotify() throws IOException {
        ArrayList<CommandsOutput> commandsOutput = new ArrayList<>();
        boolean paused = false;
        boolean loaded = false;
        boolean shuffle = false;
        String repeat = "No Repeat";
        String currentAudio = null;
        String currEp = null;
        String currentUser;
        int nrOfEp = 0;
        int time = 0;
        String selectedPlaylist = null;
        String selectedPodcast = null;
        int curr = 0;
        int prev;
        int timePassed = 0;
        users = library.getUsers();
        for(int i = 0; i < input.length; i++) {
//            if(loaded) {
//                if (!paused) {
//                    prev = curr;
//                    curr = input[i].getTimestamp();
//                    timePassed = timePassed + curr - prev;
//                } else {
//                    prev = curr;
//                    curr = input[i].getTimestamp();
//                }
//            } else {
//                prev = curr;
//                curr = input[i].getTimestamp();
//            }
            switch (input[i].getCommand()) {
                case "search" -> {
                    Search search = new Search(library.getSongs(), library.getPodcasts(), playlists);
                    search.execute(input[i], input[i].getFilters(), commandsOutput);
                    loaded = false;
                    if(currentAudio != null && currentAudio.equals(currEp)) {
                        if (!paused) {
                            prev = curr;
                            curr = input[i].getTimestamp();
                            timePassed = timePassed + curr - prev;
                        } else {
                            prev = curr;
                            curr = input[i].getTimestamp();
                        }

                        for(Podcasts podcast : library.getPodcasts()) {
                            if (selectedPodcast.equals(podcast.getName())) {
                                Episodes ep = podcast.getEpisodes().get(nrOfEp);
                                ep.setRemainingTime(ep.getDuration() - timePassed);
                            }
                        }
                    }
                    currentAudio = null;
                    selectedPlaylist = null;
                }
                case "select" -> {
                    Select select = new Select();
                    currentAudio = select.execute(input[i], commandsOutput);
                    if (currentAudio != null && isPodcast(currentAudio, library)) {
                        currentUser = input[i].getUsername();
                        for(Podcasts podcast : library.getPodcasts()) {
                            if (currentAudio.equals(podcast.getName())) {
                                if(selectedPodcast == null) {
                                    nrOfEp = 0;
                                } else {
                                    for(Podcasts podcast1 : library.getPodcasts()) {
                                        if (selectedPodcast.equals(podcast1.getName())) {
                                            Episodes ep = podcast1.getEpisodes().get(nrOfEp);
                                            if(ep.getRemainingTime() <= 0)
                                                nrOfEp ++;
                                        }
                                    }
                                }
                                selectedPodcast = podcast.getName();
                                currentAudio = podcast.getEpisodes().get(nrOfEp).getName();
                                currEp = currentAudio;
                                break;
                            }
                        }
                    } else {
                        if (currentAudio != null && isPlaylist(currentAudio, library)) {
                            currentUser = input[i].getUsername();
                            for (Playlists playlists1 : playlists) {
                                if (currentAudio.equals(playlists1.getName()) && currentUser.equals(playlists1.getOwner())) {
                                    selectedPlaylist = playlists1.getName();
                                    if (playlists1.songs != null) {
                                        currentAudio = playlists1.songs.get(0).getName();
                                        timePassed = 0;
                                        curr = input[i].getTimestamp();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                case "load" -> {
                    Load load = new Load();
                    load.execute(input[i], commandsOutput);
                    loaded = commandsOutput.get(commandsOutput.size() - 1).getMessage().equals("Playback loaded successfully.");
                    if(loaded) {
                        if(selectedPodcast == null) {
                            timePassed = 0;
                        }
                        curr = input[i].getTimestamp();
                    }
//                    curr = input[i].getTimestamp();
                    paused = false;
                }
                case "playPause" -> {
                    PlayPause playPause = new PlayPause();
                    playPause.execute(input[i], commandsOutput, paused, loaded);
                    if (!paused) {
                        prev = curr;
                        curr = input[i].getTimestamp();
                        timePassed = timePassed + curr - prev;
                    } else {
                        prev = curr;
                        curr = input[i].getTimestamp();
                    }
                    paused = !paused;
                }
                case "status" -> {
                    Status status = new Status();
                    if (!paused) {
                        prev = curr;
                        curr = input[i].getTimestamp();
                        timePassed = timePassed + curr - prev;
                    }
                    time = timePassed;
                    if (currentAudio != null) {
                        status.execute(time, library, input[i], paused, shuffle, repeat,
                                        currentAudio, commandsOutput, selectedPodcast, selectedPlaylist,
                                        playlists);
                    } else {
                        status.execute(time, library, input[i], false, false, repeat,
                                    currentAudio, commandsOutput, selectedPodcast, selectedPlaylist,
                                    playlists);
                    }
                }
                case "createPlaylist" -> {
                    CreatePlaylist createPlaylist = new CreatePlaylist();
                    if (createPlaylist.execute(input[i], commandsOutput, users)) {
                        Playlists currentPlaylist = createPlaylist.create(input[i]);
                        playlists.add(currentPlaylist);
                        addPlaylist(input[i].getUsername(), currentPlaylist, users);
                    }
                }
                case "addRemoveInPlaylist" -> {
                    AddRemoveInPlaylist addRemove = new AddRemoveInPlaylist();
                    addRemove.execute(input[i], loaded, commandsOutput, currentAudio, users, library.getSongs());
                }
                case "like" -> {
                    LikeUnlike likeUnlike = new LikeUnlike();
                    likeUnlike.execute(input[i], loaded, commandsOutput, currentAudio, users, library.getSongs());
                }
                case "showPlaylists" -> {
                    Playlists toShow = new Playlists();
                    toShow.showPlaylists(users, input[i], commandsOutput, objectMapper);
                }
                case "showPreferredSongs" -> {
                    Users pref = new Users();
                    pref.showPreferredSongs(input[i], commandsOutput, users);
                }

                default -> {
                }
            }
        }
        writeFile(commandsOutput);
    }

    public void writeFile(ArrayList<CommandsOutput> commandsOutput) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), commandsOutput);
    }

    public void addPlaylist(String name, Playlists playlist, ArrayList<Users> users) {
        for(Users user : users) {
            if (user.getUsername().equals(name)) {
                user.getPlaylists().add(playlist);
                return;
            }
        }
    }

    public boolean isPodcast(String currentAudio, Library library) {
        boolean isPodcast = false;
        for(Podcasts podcast : library.getPodcasts()) {
            if (currentAudio.equals(podcast.getName())) {
                isPodcast = true;
                break;
            }
        }
        return isPodcast;
    }

    public boolean isPlaylist(String currentAudio, Library library) {
        boolean isPlaylist = true;
        for(Songs song : library.getSongs()) {
            if (currentAudio.equals(song.getName())) {
                isPlaylist = false;
                break;
            }
        }
        return isPlaylist;
    }
}
