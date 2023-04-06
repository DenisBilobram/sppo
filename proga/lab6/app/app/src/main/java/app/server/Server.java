package app.server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
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
    public static void main(String[] args) {

        Database dataBase = new Database();
        Signal dbSignal = dataBase.connect();

        if (!dbSignal.isSucces()) {
            System.out.println(dbSignal.getMessage());
            System.exit(0);
        }

        DataReader dataReader = new DataReader(dataBase);

        PriorityQueue<LabWork> priorityQueue = dataReader.read();
        Long maxId = 0l;

        for (LabWork lab : priorityQueue) {
            if (lab.getId() > maxId) {
                maxId = lab.getId();
            }
        }

        Receiver.maxId = maxId;

        ServerSocketChannel serverChannel;
        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(7777));
        } catch (IOException e) {
            System.out.println("Не могу открыть сокет.");
            return;
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
                        System.out.println("Отключил клиента.");
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
