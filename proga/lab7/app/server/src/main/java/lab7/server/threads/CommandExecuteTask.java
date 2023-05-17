package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import lab7.app.auth.commands.AuthCommand;
import lab7.app.commands.Command;
import lab7.app.signals.ServerSignal;
import lab7.server.Server;

public class CommandExecuteTask extends Thread {

    Command command;
    SocketChannel channel;

    public CommandExecuteTask(Command command, SocketChannel channel) {
        this.command = command;
        this.channel = channel;
    }

    public void run() {

        System.out.println("Начал выполнение.");

        ServerSignal serverSignal;

        if (command instanceof AuthCommand) {
            serverSignal = auth(command);
        } else {
            serverSignal = execute(command);
        }

        try {
            serverSignal.setClientAdress(channel.getRemoteAddress());
            Server.addSignal(serverSignal);
            channel.register(Server.getSelector(), SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public ServerSignal execute(Command command) {

        return new ServerSignal(command.execute(Server.getpriorityBlockingQueue()));

    }

    public ServerSignal auth(Command command) {
        ServerSignal serverSignal = new ServerSignal(((AuthCommand)command).execute());
        serverSignal.setUser(command.getUser());
        return serverSignal;
    }

    

}
