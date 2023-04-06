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
    public static void main(String[] args) {

        ServerConnection server = new ServerConnection();
        CommandParser commandParser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        boolean connected = server.coonectToServer();

        if (connected) {

            Sender sender = new Sender(ServerConnection.getChannel());
            Reciever reciever = new Reciever(ServerConnection.getChannel());

            while (server.checkConnectiion()) {
                System.out.print("\nВведите команду. Для списка команд введите help.");
                Command command = commandParser.recieveCommand(scanner);

                if (command == null) {
                    continue;
                }

                Signal executedCommand;
                if (command instanceof ClientCommand) {
                    executedCommand = ((ClientCommand)command).execute();
                } else {
                    CommandSignal signal = new CommandSignal(command);
                    boolean sended = sender.sendCommandSignal(signal);
                    if (sended) {
                        executedCommand = reciever.getServerSignal();
                    } else {
                        executedCommand = new Signal("\nНе удалось отправить команду на сервер.");
                        executedCommand.setSucces(false);
                    }
                }
                SignalManager.handle(executedCommand);
            }
        }
    }
}


