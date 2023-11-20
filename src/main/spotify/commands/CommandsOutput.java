package main.spotify.commands;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
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
    public ArrayList<Object> result;

    @Getter @Setter
    public static class Stats{
        private String name;
        private int remainedTime;
        private String repeat;
        private boolean shuffle;
        private boolean paused;
    }

    @Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Details {
        private String name;
        public ArrayList<String> songs;
        private String visibility;
        private int followers;
    }
    public CommandsOutput() {}

    public CommandsOutput(String command, String username, int timestamp,
                           String message, ArrayList<String> results) {
        this.command = command;
        this.user = username;
        this.timestamp = timestamp;
        this.message = message;
        this.results = results;
    }
}
