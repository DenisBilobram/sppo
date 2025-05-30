package lab6.app.signals;

import lab6.app.commands.Command;

public class ClientSignal extends Signal {
    
    protected Command command;

    public ClientSignal() {
    }

    public ClientSignal(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

}
