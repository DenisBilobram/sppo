package app.server;

import java.nio.channels.ServerSocketChannel;
import java.util.PriorityQueue;

import app.commands.Command;
import app.labwork.LabWork;
import app.server.database.DataReader;
import app.server.database.Database;
import app.server.network.ClientConnection;
import app.server.network.Receiver;
import app.server.network.Sender;
import app.signals.Signal;


public class Server {
    public static void startServer(int host) {

        Database dataBase = new Database();
        Signal dbSignal = dataBase.connect();

        if (!dbSignal.isSucces()) {
            System.out.println(dbSignal.getMessage());
            System.exit(0);
        }

        DataReader dataReader = new DataReader(dataBase);

        PriorityQueue<LabWork> priorityQueue = dataReader.read();
        
        Receiver.setMaxId(priorityQueue);

        ServerSocketChannel serverChannel = ClientConnection.openChannel(host);

        if (serverChannel == null) {
            System.out.println("Не могу открыть сокет.");
            System.exit(0);
        }

        while (true) {

            ClientConnection client = new ClientConnection();
            
            System.out.println("Жду клиента...");
            boolean connected = client.connectToClient(serverChannel);
            System.out.println("Подключился клиент.");

            Receiver reciver = new Receiver(client.getInputStream());
            Sender sender = new Sender(client.getOutputStream());

            
            if (connected) {

                while (client.checkConnectiion()) {
                    System.out.println("Жду команду...");
                    Command command = reciver.recieveCommand();
                    System.out.println("Получил команду.");
                    if (command == null) {
                        client.disconnect();
                        System.out.println("Соединение с клиентом прервано.");
                        continue;
                    }
                    Signal serverSignal = command.execute(priorityQueue);
                    dataBase.save(priorityQueue);
                    sender.sendSignal(serverSignal);
                }
            }
        }
    }
}
