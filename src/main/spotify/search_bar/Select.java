package main.spotify.search_bar;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;

@Getter @Setter
public class Select {
    private CommandsInput.Filters filters;

    public Select() {
    }
    public void execute(CommandsInput command, CommandsInput.Filters filters) {
        return;
    }
}
