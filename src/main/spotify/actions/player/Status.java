package main.spotify.actions.player;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Library;
import main.spotify.data.Songs;

import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public class Status {

    private CommandsOutput currentCommand = new CommandsOutput();

    public Status() {}

    public void execute(int time, Library library, CommandsInput command, boolean paused, boolean shuffle,
                                        String repeat, String currentAudio, ArrayList<CommandsOutput> commandsOutputs) {

        int duration = 0;
        ArrayList<Songs> songs = library.getSongs();
        for(Songs song : songs) {
            if(Objects.equals(song.getName(), currentAudio)) {
                duration = song.getDuration() - time;
                set(currentCommand, command, paused, shuffle, repeat, currentAudio, duration);
                break;
            }
        }
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsOutput currentCommand, CommandsInput command, boolean paused,
                                boolean shuffle, String repeat, String currentAudio, int remaining) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
        CommandsOutput.Stats stats = new CommandsOutput.Stats();
        stats.setName(currentAudio);
        stats.setShuffle(shuffle);
        stats.setRepeat(repeat);
        stats.setPaused(paused);
        if(remaining <= 0) {
            remaining = 0;
            stats.setName("");
            stats.setPaused(true);
        }
        stats.setRemainedTime(remaining);
        currentCommand.setStats(stats);
    }
}

