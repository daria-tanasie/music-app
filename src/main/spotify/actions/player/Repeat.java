package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;

import java.util.ArrayList;

public final class Repeat {

    private final CommandsOutput currentCommand = new CommandsOutput();
    private final int three = 3;

    public Repeat() {
    }

    /**
     * method that will execute the repeat command
     * @param command
     * @param commandsOutputs
     * @param loaded
     * @param repeatCode
     * @param selectedPlaylist
     * @return
     */

    public int execute(final CommandsInput command,
                       final ArrayList<CommandsOutput> commandsOutputs,
                       final boolean loaded, int repeatCode,
                       final String selectedPlaylist) {
        if (!loaded) {
            set(command);
            currentCommand.setMessage("Please load a source before setting the repeat status.");
            commandsOutputs.add(currentCommand);
            return repeatCode;
        }

        String repeat = null;
        repeatCode++;
        if (repeatCode == three) {
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
            default -> {

            }
        }
        set(command);
        currentCommand.setMessage("Repeat mode changed to " + repeat + ".");
        commandsOutputs.add(currentCommand);
        return  repeatCode;
    }

    /**
     * sets the output for the current command
     * @param command
     */

    public  void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }

    /**
     * method that will give the repeat stage based on the code(0, 1, 2)
     * @param repeatCode
     * @param currentAudio
     * @param repeat
     * @param selectedPlaylist
     * @return
     */

    public String getRepeat(final int repeatCode, final String currentAudio,
                            String repeat, final String selectedPlaylist) {
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
            default -> {

            }
        }
        return repeat;
    }

    /**
     * method that will return the code of repeat based on the string
     * @param repeat
     * @return
     */

    public int getRepeatCom(final String repeat) {
        if (repeat.equals("No Repeat")) {
            return 0;
        }
        if (repeat.equals("Repeat Once") || repeat.equals("Repeat All")) {
            return 1;
        }
        return 2;
    }

}
