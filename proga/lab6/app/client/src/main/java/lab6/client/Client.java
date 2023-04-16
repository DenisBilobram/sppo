package lab6.client;
import java.util.Scanner;

import lab6.client.commands.ClientCommand;
import lab6.client.input.CommandParser;
import lab6.client.network.Reciever;
import lab6.client.network.Sender;
import lab6.client.network.ServerConnection;
import lab6.app.commands.Command;
import lab6.app.signals.ClientSignal;
import lab6.app.signals.Signal;
import lab6.app.signals.SignalManager;

public class Client {

    public static void main(String[] args) {
        if (args.length != 2 || !isPort(args[1])) {
            System.out.println("Неверный формат аргументов: {host} {port}");
            System.exit(0);
        }
        
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        ServerConnection server = new ServerConnection();
        CommandParser commandParser = new CommandParser();
        Scanner scanner = new Scanner(System.in);

        boolean connected = server.coonectToServer(host, port);

        if (connected) {

            Sender sender = new Sender(ServerConnection.getChannel());
            Reciever reciever = new Reciever(ServerConnection.getChannel());

            while (server.checkConnectiion()) {
                
                System.out.println();
                SignalManager.printMessage("Введите команду. Для списка команд введите help.", true);
                Command command = commandParser.recieveCommand(scanner, true);

                if (command == null) {
                    continue;
                }

                Signal responseSignal;

                if (command instanceof ClientCommand) {

                    responseSignal = ((ClientCommand)command).execute();

                } else {

                    ClientSignal signal = new ClientSignal(command);
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
}


