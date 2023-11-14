package main.spotify.commands;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommandsOutput {
    private String command;
    private String username;
    private int timestamp;
    private String message;
    private ArrayList<String> results;

    public CommandsOutput() {
    }

    public CommandsOutput(String command, String username, int timestamp,
                           String message, ArrayList<String> results) {
        this.command = command;
        this.username = username;
        this.timestamp = timestamp;
        this.message = message;
        this.results = results;
    }

}
