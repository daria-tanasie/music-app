package main.spotify.commands;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandsOutput {
    private String command;
    private String user;
    private int timestamp;
    private String message;
    public ArrayList<String> results;
    private Stats stats;

    @Getter @Setter
    public static class Stats{
        private String name;
        private int remainedTime;
        private String repeat;
        private boolean shuffle;
        private boolean paused;
    }

    public CommandsOutput() {
    }

    public CommandsOutput(String command, String username, int timestamp,
                           String message, ArrayList<String> results) {
        this.command = command;
        this.user = username;
        this.timestamp = timestamp;
        this.message = message;
        this.results = results;
    }

}
