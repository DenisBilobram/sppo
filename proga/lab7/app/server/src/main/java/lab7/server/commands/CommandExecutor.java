package lab7.server.commands;

import lab7.app.auth.commands.AuthCommand;
import lab7.app.commands.Command;
import lab7.app.signals.ServerSignal;
import lab7.server.Server;

public class CommandExecutor {
    
    public ServerSignal execute(Command command) {

        return new ServerSignal(command.execute(Server.getPriorityQueue()));

    }

    public ServerSignal auth(Command command) {
        ServerSignal serverSignal = new ServerSignal(((AuthCommand)command).execute());
        serverSignal.setUser(command.getUser());
        return serverSignal;
    }

}
