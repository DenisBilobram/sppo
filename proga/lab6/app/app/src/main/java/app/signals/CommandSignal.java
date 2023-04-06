package app.signals;

import app.commands.Command;

public class CommandSignal extends Signal {
    
    protected Command command;

    public CommandSignal() {
    }

    public CommandSignal(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

}
