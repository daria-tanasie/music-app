package main.spotify.actions.playlist_comm;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Songs;
import main.spotify.data.Users;

import java.util.ArrayList;

@Getter @Setter
public class Playlists {
    private String name;
    private String owner;
    private int id;
    public ArrayList<Songs> songs;
    private String visibility = "public";
    private int followers = 0;

    public Playlists(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public Playlists() {}

    public void showPlaylists(ArrayList<Users> users, CommandsInput command,
                              ArrayList<CommandsOutput> commandsOutputs, ObjectMapper objectMapper) throws JsonProcessingException {

        Users user = new Users();

        for(Users iter : users) {
            if(iter.getUsername().equals(command.getUsername())) {
                user = iter;
                break;
            }
        }

        ArrayList<Playlists> playlists = user.playlists;

        CommandsOutput currentCommand = new CommandsOutput();
        set(command, currentCommand);

        CommandsOutput.Details details = new CommandsOutput.Details();
        details.songs = new ArrayList<>();

        for(Playlists playlist : playlists) {
            details.setName(playlist.getName());
            if(playlist.songs != null)
                for(int i = 0; i < playlist.songs.size(); i++) {
                    details.songs.add(playlist.songs.get(i).getName());
                }
            details.setVisibility(playlist.getVisibility());
            details.setFollowers(playlist.getFollowers());

            if(currentCommand.result == null) {
                currentCommand.result = new ArrayList<>();
            }
            currentCommand.result.add(details);
            //currentCommand.res.add(cleanJson);
            //currentCommand.res.add();
        }
        commandsOutputs.add(currentCommand);
    }

    public void set(CommandsInput command, CommandsOutput currentCommand) {
        currentCommand.setCommand(command.getCommand());
        currentCommand.setTimestamp(command.getTimestamp());
        currentCommand.setUser(command.getUsername());
    }
}
