package lab8.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import lab8.app.auth.commands.AuthCommand;
import lab8.app.commands.Command;
import lab8.app.signals.ServerSignal;
import lab8.server.Server;

public class CommandExecuteTask extends Thread {

    Command command;
    SocketChannel channel;

    public CommandExecuteTask(Command command, SocketChannel channel) {
        this.command = command;
        this.channel = channel;
    }

    public void run() {

        ServerSignal serverSignal;

        if (command instanceof AuthCommand) {
            serverSignal = auth(command);
        } else {
            serverSignal = execute(command);
            System.out.println("Команда выполнена.");
        }

        try {
            serverSignal.setClientAdress(channel.getRemoteAddress());
            Server.addSignal(serverSignal);
            channel.register(Server.getSelector(), SelectionKey.OP_WRITE);
            Server.getSelector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public ServerSignal execute(Command command) {

        return command.execute(Server.getpriorityBlockingQueue());

    }

    public ServerSignal auth(Command command) {
        ServerSignal serverSignal = new ServerSignal(((AuthCommand)command).execute());
        serverSignal.setUser(command.getUser());
        return serverSignal;
    }

    

}
