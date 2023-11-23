package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Library;

import java.util.ArrayList;

public final class Repeat {

    private final CommandsOutput currentCommand = new CommandsOutput();

    public Repeat() {
    }

    public int execute(CommandsInput command, ArrayList<CommandsOutput> commandsOutputs,
                        boolean loaded, int repeatCode, final String selectedPlaylist) {
        if (!loaded) {
            set(currentCommand, command);
            currentCommand.setMessage("Please load a source before setting the repeat status.");
            commandsOutputs.add(currentCommand);
            return repeatCode;
        }

        String repeat = null;
        repeatCode++;
        if (repeatCode == 3) {
            repeatCode = 0;
        }

        switch (repeatCode) {
            case 0 -> {
                repeat = "no repeat";
            }
            case 1 -> {
                if (selectedPlaylist != null) {
                    repeat = "repeat all";
                } else {
                    repeat = "repeat once";
                }
            }
            case 2 -> {
                if (selectedPlaylist != null) {
                    repeat = "repeat current song";
                } else {
                    repeat = "repeat infinite";
                }
            }
        }
        set(currentCommand, command);
        currentCommand.setMessage("Repeat mode changed to " + repeat + ".");
        commandsOutputs.add(currentCommand);
        return  repeatCode;
    }

    public  void set(CommandsOutput currentCommand, CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }

    public String getRepeat(int repeatCode, String currentAudio, String repeat,
                                String selectedPlaylist) {
        if (currentAudio == null) {
            return repeat;
        }
        switch (repeatCode) {
            case 0 -> {
                repeat = "No Repeat";
            }
            case 1 -> {
                if (selectedPlaylist != null) {
                    repeat = "Repeat All";
                } else {
                    repeat = "Repeat Once";
                }
            }
            case 2 -> {
                if (selectedPlaylist != null) {
                    repeat = "Repeat Current Song";
                } else {
                    repeat = "Repeat Infinite";
                }
            }
        }
        return repeat;
    }

    public int getRepeatCom(String repeat) {
        if(repeat.equals("No Repeat")) {
            return 0;
        }
        if(repeat.equals("Repeat Once") || repeat.equals("Repeat All")) {
            return 1;
        }
        return 2;
    }

}
