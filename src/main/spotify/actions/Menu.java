package main.spotify.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import main.spotify.commands.CommandsInput;
import main.spotify.commands.CommandsOutput;
import main.spotify.data.Library;
import main.spotify.search_bar.Search;
import main.spotify.search_bar.Select;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Menu {
    private CommandsInput[] input;
    private ArrayNode output;
    //private Actions actions;
    private Library library;
    private Search search;
    private Select select;
    private CommandsOutput commandsOutput;

    public Menu(CommandsInput[] input, ArrayNode output, Library library) {
        this.input = input;
        this.output = output;
        //this.actions = actions;
        this.library = library;
    }

    public void actionsSpotify() {
        for(int i = 0; i < input.length; i++) {
            switch (input[i].getCommand()) {
                case "search" -> {
                    Search search = new Search(library.getSongs(), library.getPodcasts());
                    search.execute(input[i], input[i].getFilters());}
                case "select" -> System.out.println("nu");
                default -> {
                    return;
                }
            }
        }
    }


}
