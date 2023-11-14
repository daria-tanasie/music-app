package main.spotify.actions;

import lombok.Getter;
import lombok.Setter;
import main.spotify.commands.CommandsInput;

@Getter @Setter
public class Actions {
    private CommandsInput[] commandsInput;
    private CommandsInput.Filters filters;

    public Actions(CommandsInput commandsInput, CommandsInput.Filters filters) {
        this.commandsInput = new CommandsInput[]{commandsInput};
        this.filters = filters;
    }

    public void doAction(CommandsInput[] commandsInput){
        for(CommandsInput command : commandsInput) {

        }
    }
}
