package main.spotify.actions.player;

import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Episodes;
import main.spotify.data.Podcasts;

import java.util.ArrayList;

public class ForwardBackward {
    private final CommandsOutput currentCommand = new CommandsOutput();
    private final ArrayList<CommandsOutput> commandsOutputs;
    private final ArrayList<Podcasts> podcasts;
    private final int nineteen = 90;

    public ForwardBackward(final ArrayList<CommandsOutput> commandsOutputs,
                    final ArrayList<Podcasts> podcasts) {
        this.commandsOutputs = commandsOutputs;
        this.podcasts = podcasts;
    }

    /**
     * method to forward the episode
     * @param command
     * @param loaded
     * @param currentAudio
     * @param selectedPodcast
     * @param time
     * @return
     */

public int executeFw(final CommandsInput command, final boolean loaded,
                      final String currentAudio, final String selectedPodcast,
                      int time) {

    set(command);

    if (!loaded) {
        currentCommand.setMessage("Please load a source before attempting to forward.");
        commandsOutputs.add(currentCommand);
        return 0;
    }
    if (selectedPodcast != null) {
        for (Podcasts podcast : podcasts) {
            if (selectedPodcast.equals(podcast.getName())) {
                time = time + nineteen;
                currentCommand.setMessage("Skipped forward successfully.");
                commandsOutputs.add(currentCommand);
                return time;
            }
        }
    }

    currentCommand.setMessage("The loaded source is not a podcast.");
    commandsOutputs.add(currentCommand);
    return 0;
}

    /**
     * method to backward the episode
     * @param command
     * @param loaded
     * @param currentAudio
     * @param selectedPodcast
     * @param time
     * @return
     */
    public int executeBw(final CommandsInput command, final boolean loaded,
                         final String currentAudio, final String selectedPodcast,
                         int time) {

        set(command);

        if (!loaded) {
            currentCommand.setMessage("Please select a source before rewinding.");
            commandsOutputs.add(currentCommand);
            return 0;
        }
        if (selectedPodcast != null) {
            for (Podcasts podcast : podcasts) {
                if (selectedPodcast.equals(podcast.getName())) {
                    for (Episodes episode : podcast.getEpisodes()) {
                        if (currentAudio.equals(episode.getName())) {
                            time = time - nineteen;
                            currentCommand.setMessage("Rewound successfully.");
                            commandsOutputs.add(currentCommand);
                            return time;
                        }
                    }
                }
            }
        }

        currentCommand.setMessage("The loaded source is not a podcast.");
        commandsOutputs.add(currentCommand);
        return 0;
    }

    /**
     * method that sets the output
     * @param command
     */

    public void set(final CommandsInput command) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setUser(command.getUsername());
        currentCommand.setTimestamp(command.getTimestamp());
    }
}
