package lab7.client;
import java.util.Scanner;

import lab7.client.auth.Authenticator;
import lab7.client.network.Reciever;
import lab7.client.network.Sender;
import lab7.client.network.ServerConnection;
import lab7.app.auth.User;
import lab7.app.auth.commands.AuthCommand;
import lab7.app.auth.commands.LogoutCommand;
import lab7.app.commands.Command;
import lab7.app.commands.CommandExecute;
import lab7.app.input.CommandParser;
import lab7.app.signals.ClientSignal;
import lab7.app.signals.Signal;
import lab7.app.signals.SignalManager;

public class Client {

    private static User user = null;
    private static ServerConnection server;


    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2 || !isPort(args[1])) {
            System.out.println("Неверный формат аргументов: {host} {port}");
            System.exit(0);
        }
        
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        server = new ServerConnection(host, port);

        if (!server.checkConnectiion()) {
            return;
        }

        CommandParser commandParser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        Sender sender = new Sender(ServerConnection.getChannel());
        Reciever reciever = new Reciever(ServerConnection.getChannel());

        while (true) {
            while (Client.user == null) {
                Authenticator authenticator = new Authenticator(sender, reciever);
                Client.user = authenticator.authentication();
                if (Client.user == null) {
                    System.out.println("Потеряно соединение с сервером...");
                    server.disconnect();
                    server = server.reconnect();
                    sender = new Sender(ServerConnection.getChannel());
                    reciever = new Reciever(ServerConnection.getChannel());
                }

            }
            
            System.out.println();
            SignalManager.printMessage("Введите команду. Для списка команд введите help.", true);
            Command command = commandParser.recieveCommand(scanner, true, Client.user);

            if (command == null) {
                continue;
            } 

            Signal responseSignal;

            if (command instanceof CommandExecute) {
                responseSignal = ((CommandExecute)command).pull();
                System.out.println("\n" + responseSignal.getMessage() + "\n");
                if (((CommandExecute)command).getListOfCommands().size() == 0) {
                    continue;
                }
            }

            ClientSignal signalToSend = new ClientSignal(command);
            
            sender.sendClientSignal(signalToSend);
            

            responseSignal = reciever.getServerSignal();
            if (responseSignal == null) {
                System.out.println("Потеряно соединение с сервером...");
                server.disconnect();
                server = server.reconnect();
                sender = new Sender(ServerConnection.getChannel());
                reciever = new Reciever(ServerConnection.getChannel());
                sender.sendClientSignal(signalToSend);
                responseSignal = reciever.getServerSignal();
            }

            if (responseSignal != null) {
                if (command instanceof AuthCommand && responseSignal.isSucces()) {
                    if (command instanceof LogoutCommand) {
                        Client.user = null;
                    } else {
                        Client.user = command.getUser();
                    }
                }
                SignalManager.handle(responseSignal);
            }
            
        }
    }
    
    public static boolean isPort(String port) {
        try {
            int portNum = Integer.parseInt(port);
            if (portNum > 0 && portNum < 65536) {
                return true;
            }
        } catch (NumberFormatException exp) {
            
        }
        return false;
    }

    public static ServerConnection getServer() {
        return server;
    }

    public static void setServer(ServerConnection server) {
        Client.server = server;
    }

    public static User getUser() {
        return user;
    }
}


