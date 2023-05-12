package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import lab7.app.auth.commands.AuthCommand;
import lab7.app.commands.Command;
import lab7.app.signals.ServerSignal;
import lab7.server.Server;
import lab7.server.commands.CommandExecutor;
import lab7.server.network.Receiver;
import lab7.server.network.Sender;

public class ServerThread extends Thread  {

    private SocketChannel channel;
    private Receiver receiver;
    private Sender sender;
    private CommandExecutor commandExecutor = new CommandExecutor();


    public ServerThread(SocketChannel channel) {
        this.channel = channel;
        this.receiver = new Receiver(channel);
        this.sender = new Sender(channel);

    }

    public void run() {
        System.out.println(this.getName()+ ": Начал работу с клиентом...");
        while (true) {

            Command command = receiver.recieveCommands();

            if (command == null) {
                try {
                    System.out.println(this.getName()+ ": Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                    channel.close();
                    Server.getSession().remove(channel);
                } catch (IOException exp) {
                }
                break;
            }

            System.out.println(this.getName()+ ": Получил команду.");

            ServerSignal serverSignal;


            if (command instanceof AuthCommand) {
                serverSignal = commandExecutor.auth(command);
            } else {
                serverSignal = commandExecutor.execute(command);
            }

            boolean sended = sender.sendSignal(serverSignal);

            if (sended) {
                System.out.println(this.getName()+ ": Отправил сигнал клиенту.");
            } else {
                try {
                    System.out.println(this.getName()+ ": Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                    channel.close();
                    Server.getSession().remove(channel);
                } catch (IOException exp) {
                }
                break;
            }

        }
        
    }
    
}
