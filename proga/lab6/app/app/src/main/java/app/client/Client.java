package app.client;
import java.util.Scanner;

import app.client.input.CommandParser;
import app.client.network.Reciever;
import app.client.network.Sender;
import app.client.network.ServerConnection;
import app.commands.Command;
import app.commands.client.ClientCommand;
import app.signals.CommandSignal;
import app.signals.Signal;
import app.signals.SignalManager;

public class Client {
    public static void startClient(String host, int port) {

        ServerConnection server = new ServerConnection();
        CommandParser commandParser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        boolean connected = server.coonectToServer(host, port);

        if (connected) {

            Sender sender = new Sender(ServerConnection.getChannel());
            Reciever reciever = new Reciever(ServerConnection.getChannel());

            while (server.checkConnectiion()) {
                System.out.print("Введите команду. Для списка команд введите help.");
                Command command = commandParser.recieveCommand(scanner);

                if (command == null) {
                    continue;
                }

                Signal responseSignal;
                if (command instanceof ClientCommand) {
                    responseSignal = ((ClientCommand)command).execute();
                } else {
                    CommandSignal signal = new CommandSignal(command);
                    boolean sended = sender.sendCommandSignal(signal);
                    if (sended) {
                        responseSignal = reciever.getServerSignal();
                    } else {
                        responseSignal = new Signal("Не удалось отправить команду на сервер.");
                        responseSignal.setSucces(false);
                    }
                }
                SignalManager.handle(responseSignal);
            }
        }
    }
}


