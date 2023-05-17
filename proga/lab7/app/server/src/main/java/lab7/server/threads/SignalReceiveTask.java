package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import lab7.app.commands.Command;
import lab7.server.Server;
import lab7.server.network.Receiver;
import lab7.server.network.Sender;

public class SignalReceiveTask extends RecursiveAction{

    SocketChannel channel;
    Receiver receiver;
    Sender sender;

    public SignalReceiveTask(SocketChannel channel) {
        this.channel = channel;
        this.receiver = new Receiver(channel);
        this.sender = new Sender(channel);
    }

    @Override
    protected void compute() {
        Command command = receiver.recieveCommands();

        if (command == null) {
            try {
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                Server.getSession().remove(channel);
            } catch (IOException exp) {
                System.out.println("Ошибка во время получения команды.");
                exp.printStackTrace();
            }
        }

        System.out.println("Получил команду.");

        Server.createExecuteTask(command, channel);

    }
    
}
