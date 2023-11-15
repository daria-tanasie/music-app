package main.spotify.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.spotify.actions.player.Load;
import main.spotify.actions.player.PlayPause;
import main.spotify.actions.player.Status;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Library;
import main.spotify.actions.search_bar.Search;
import main.spotify.actions.search_bar.Select;

import lombok.Getter;
import lombok.Setter;
import main.spotify.data.Songs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private CommandsInput[] input;
    private Library library;
    private ArrayList<CommandsOutput> commandsOutput = new ArrayList<>();
    private String filePathOutput;
    private ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public Menu(CommandsInput[] input, String filePathOutput, Library library) {
        this.input = input;
        this.filePathOutput = filePathOutput;
        this.library = library;
    }

    public void actionsSpotify() throws IOException {
        boolean paused = false;
        boolean loaded = false;
        boolean shuffle = false;
        String repeat = "No Repeat";
        String currentAudio = null;
        int loadPos = 0;
        int curr = 0;
        int prev = 0;
        int timePassed = 0;
        int timePaused = 0;
        for(int i = 0; i < input.length; i++) {
            switch (input[i].getCommand()) {
                case "search" -> {
                    Search search = new Search(library.getSongs(), library.getPodcasts());
                    search.execute(input[i], input[i].getFilters(), commandsOutput);
                }
                case "select" -> {
                    Select select = new Select();
                    currentAudio = select.execute(input[i], commandsOutput);
                }
                case "load" -> {
                    Load load = new Load();
                    load.execute(input[i], commandsOutput);
                    loaded = true;
                    timePassed = 0;
                    curr = input[i].getTimestamp();
                }
                case "playPause" -> {
                    PlayPause playPause = new PlayPause();
                    playPause.execute(input[i], commandsOutput, paused, loaded);
                    if(!paused) {
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
                    int time = 0;
                    if(!paused) {
                        prev = curr;
                        curr = input[i].getTimestamp();
                        timePassed = timePassed + curr - prev;
                    }
                    time = timePassed;
                    if(currentAudio != null) {
                        status.execute(time, library, input[i], paused, shuffle, repeat,
                                        currentAudio, commandsOutput);
                    }
                }

                default -> {
                }
            }
        }
        writeFile();
    }

    public void writeFile() throws IOException {
        objectWriter.writeValue(new File(filePathOutput), commandsOutput);
    }
}
