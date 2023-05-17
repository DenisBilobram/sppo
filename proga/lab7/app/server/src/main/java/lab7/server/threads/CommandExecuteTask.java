package lab7.server.threads;

import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import lab7.app.auth.commands.AuthCommand;
import lab7.app.commands.Command;
import lab7.app.signals.ServerSignal;
import lab7.server.Server;

public class CommandExecuteTask extends RecursiveAction {

    Command command;
    SocketChannel channel;

    public CommandExecuteTask(Command command, SocketChannel channel) {
        this.command = command;
        this.channel = channel;
    }

    @Override
    protected void compute() {

        ServerSignal serverSignal;

        if (command instanceof AuthCommand) {
            serverSignal = auth(command);
        } else {
            serverSignal = execute(command);
        }

        Server.createSendTask(channel, serverSignal);

    }
    
    public ServerSignal execute(Command command) {

        return new ServerSignal(command.execute(Server.getPriorityQueue()));

    }

    public ServerSignal auth(Command command) {
        ServerSignal serverSignal = new ServerSignal(((AuthCommand)command).execute());
        serverSignal.setUser(command.getUser());
        return serverSignal;
    }

    

}
