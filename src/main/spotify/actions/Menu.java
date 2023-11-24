package main.spotify.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.spotify.actions.player.Load;
import main.spotify.actions.player.PlayPause;
import main.spotify.actions.player.Repeat;
import main.spotify.actions.player.Status;
import main.spotify.actions.playlist_comm.Playlists;
import main.spotify.actions.playlist_comm.LikeUnlike;
import main.spotify.actions.playlist_comm.AddRemoveInPlaylist;
import main.spotify.actions.playlist_comm.CreatePlaylist;
import main.spotify.actions.playlist_comm.SwitchVisibility;
import main.spotify.actions.playlist_comm.Follow;
import main.spotify.actions.trending.GetTop5;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Library;
import main.spotify.data.Episodes;
import main.spotify.data.Users;
import main.spotify.data.Podcasts;
import main.spotify.actions.search_bar.Search;
import main.spotify.actions.search_bar.Select;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
public final class Menu {
    private final CommandsInput[] input;
    private final Library library;
    private ArrayList<Playlists> playlists = new ArrayList<>();
    private ArrayList<Users> users = new ArrayList<>();
    private final String filePathOutput;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Menu(final CommandsInput[] input,
                final String filePathOutput, final Library library) {
        this.input = input;
        this.filePathOutput = filePathOutput;
        this.library = library;
    }

    /**
     * acts like a menu for the commands, deciding which one to execute
     * @throws IOException
     */

    public void actionsSpotify() throws IOException {
        ArrayList<CommandsOutput> commandsOutput = new ArrayList<>();
        boolean paused = false, loaded = false, shuffle = false;
        String repeat = "No Repeat";
        int repeatCode = 0, lastTime = 0;
        String currentAudio = null;
        String currEp = null;
        int nrOfEp = 0, time;
        String selectedPlaylist = null;
        String selectedPodcast = null;
        int curr = 0, prev, timePassed = 0;
        users = library.getUsers();
        for (int i = 0; i < input.length; i++) {
            switch (input[i].getCommand()) {
                case "search" -> {
                    Search search = new Search(library.getSongs(),
                            library.getPodcasts(), playlists);
                    search.execute(input[i], input[i].getFilters(), commandsOutput);
                    loaded = false;
                    if (currentAudio != null && currentAudio.equals(currEp)) {
                        if (!paused) {
                            prev = curr;
                            curr = input[i].getTimestamp();
                            timePassed = timePassed + curr - prev;
                        } else {
                            prev = curr;
                            curr = input[i].getTimestamp();
                        }

                        for (Podcasts podcast : library.getPodcasts()) {
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
                    Select select = new Select(currentAudio);
                    currentAudio = select.execute(input[i], commandsOutput);
                    if (currentAudio != null && isPodcast(currentAudio)) {
                        for (Podcasts podcast : library.getPodcasts()) {
                            if (currentAudio.equals(podcast.getName())) {
                                if (selectedPodcast == null) {
                                    nrOfEp = 0;
                                } else {
                                    for (Podcasts podcast1 : library.getPodcasts()) {
                                        if (selectedPodcast.equals(podcast1.getName())) {
                                            Episodes ep = podcast1.getEpisodes().get(nrOfEp);
                                            if (ep.getRemainingTime() <= 0) {
                                                nrOfEp++;
                                            }
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
                        if (currentAudio != null && isPlaylist(currentAudio)) {
                            for (Playlists playlists1 : playlists) {
                                if (currentAudio.equals(playlists1.getName())) {
                                    selectedPlaylist = playlists1.getName();
                                    if (playlists1.songs != null) {
                                        currentAudio = playlists1.songs.get(0).getName();
                                        timePassed = 0;
                                        curr = input[i].getTimestamp();
                                    } else {
                                        currentAudio = null;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                case "load" -> {
                    repeat = "No Repeat";
                    repeatCode = 0;
                    Load load = new Load(loaded);
                    load.execute(input[i], commandsOutput);
                    if (currentAudio != null) {
                        loaded = true;
                    }
                    if (commandsOutput.get(commandsOutput.size() - 1)
                            .getMessage().equals("Playback loaded successfully.")) {
                        if (selectedPodcast == null) {
                            timePassed = 0;
                        }
                        curr = input[i].getTimestamp();
                    }
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
                    Status status = new Status(library, commandsOutput, loaded);
                    Repeat updateRepeat = new Repeat();
                    if (!paused) {
                        prev = curr;
                        curr = input[i].getTimestamp();
                        timePassed = timePassed + curr - prev;
                    }
                    time = timePassed;

                    if (currentAudio != null) {
                        repeat = status.execute(time, lastTime, input[i], paused, shuffle, repeat,
                                        currentAudio, selectedPodcast, selectedPlaylist, playlists);
                    } else {
                        repeat = status.execute(time, lastTime, input[i], false, false, repeat,
                                    null, selectedPodcast, selectedPlaylist, playlists);
                    }

                    repeatCode = updateRepeat.getRepeatCom(repeat);
                }
                case "createPlaylist" -> {
                    CreatePlaylist createPlaylist = new CreatePlaylist();
                    if (createPlaylist.execute(input[i], commandsOutput, users)) {
                        Playlists currentPlaylist = createPlaylist.create(input[i]);
                        playlists.add(currentPlaylist);
                        selectedPlaylist = currentPlaylist.getName();
                        addPlaylist(input[i].getUsername(), currentPlaylist);
                    }
                }
                case "addRemoveInPlaylist" -> {

                    AddRemoveInPlaylist addRemove = new AddRemoveInPlaylist();
                    addRemove.execute(input[i], loaded, commandsOutput,
                                        currentAudio, users, library.getSongs());
                }
                case "like" -> {
                    LikeUnlike likeUnlike = new LikeUnlike();
                    likeUnlike.execute(input[i], loaded, commandsOutput, currentAudio,
                                        users, library.getSongs());
                }
                case "showPlaylists" -> {
                    Playlists toShow = new Playlists();
                    toShow.showPlaylists(users, input[i], commandsOutput);
                }
                case "showPreferredSongs" -> {
                    Users pref = new Users();
                    pref.showPrefSongs(input[i], commandsOutput, users);
                }
                case "repeat" -> {
                    Repeat repeatCom = new Repeat();
                    repeatCode = repeatCom.execute(input[i], commandsOutput, loaded,
                                repeatCode, selectedPlaylist);
                    repeat = repeatCom.getRepeat(repeatCode, currentAudio,
                            repeat, selectedPlaylist);
                    if (repeatCode == 0) {
                        prev = curr;
                        curr = input[i].getTimestamp();
                    }
                }
                case "getTop5Playlists" -> {
                    GetTop5 getTop5 = new GetTop5(commandsOutput);
                    getTop5.executeP(input[i], playlists);
                }
                case "getTop5Songs" -> {
                    GetTop5 getTop5 = new GetTop5(commandsOutput);
                    getTop5.executeS(input[i], library.getSongs());
                }
                default -> {
                }
            }
        }
        writeFile(commandsOutput);
    }

    /**
     * method used to write the commands in json file
     * @param commandsOutput
     * @throws IOException
     */

    public void writeFile(final ArrayList<CommandsOutput> commandsOutput) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), commandsOutput);
    }

    /**
     * adds a playlist to user class
     * @param name
     * @param playlist
     */

    public void addPlaylist(final String name, final Playlists playlist) {
        for (Users user : users) {
            if (user.getUsername().equals(name)) {
                user.getPlaylists().add(playlist);
                return;
            }
        }
    }

    /**
     * returns true if currentAudio is podcast, false otherwise
     * @param currentAudio
     * @return
     */

    public boolean isPodcast(final String currentAudio) {
        boolean isPodcast = false;
        if (currentAudio != null) {
            for (Podcasts podcast : library.getPodcasts()) {
                if (currentAudio.equals(podcast.getName())) {
                    isPodcast = true;
                    break;
                }
            }
        }
        return isPodcast;
    }

    /**
     * returns true if currentAudio, false otherwise
     * @param currentAudio
     * @return
     */

    public boolean isPlaylist(final String currentAudio) {
        boolean isPlaylist = true;
        if (currentAudio != null) {
            for (Songs song : library.getSongs()) {
                if (currentAudio.equals(song.getName())) {
                    isPlaylist = false;
                    break;
                }
            }
        }
        return isPlaylist;
    }
}
