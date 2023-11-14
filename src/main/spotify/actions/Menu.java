package main.spotify.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Library;
import main.spotify.search_bar.Search;
import main.spotify.search_bar.Select;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter @Setter @JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private CommandsInput[] input;
    private ArrayNode output;
    private Library library;
    private Search search;
    private Select select;
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
        for(int i = 0; i < input.length; i++) {
            switch (input[i].getCommand()) {
                case "search" -> {
                    Search search = new Search(library.getSongs(), library.getPodcasts());
                    search.execute(input[i], input[i].getFilters(), commandsOutput);
                }
                case "select" -> {
                    Select select = new Select();
                    select.execute(input[i], commandsOutput);
                }
                default -> {
                    return;
                }
            }
        }
        writeFile();
    }

    public void writeFile() throws IOException {
        objectWriter.writeValue(new File(filePathOutput), commandsOutput);
    }


}
