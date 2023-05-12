package lab7.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lab7.server.threads.ConsoleReader;
import lab7.server.threads.ServerThread;
import lab7.app.database.DataBase;
import lab7.app.database.DataSync;
import lab7.app.labwork.LabWork;

public class Server {

    private static volatile PriorityQueue<LabWork> priorityQueue;
    private static DataBase dataBase;
    private static InetSocketAddress address;
    private static Set<SocketChannel> session;
    public static ExecutorService service;

    public static void main(String args[]) {

        if (args.length != 1 || !isPort(args[0])) {
            System.out.println("Неверный формат аргументов: {port}");
            System.exit(0);
        }

        address = new InetSocketAddress(Integer.parseInt(args[0]));
        session = new HashSet<SocketChannel>();

        Connection connection = DataBase.getDataBasConnection();

        Server.priorityQueue = DataSync.getQueueFromDataBase(connection);
        Thread consoleReader = new ConsoleReader();
        consoleReader.setDaemon(true);
        consoleReader.start();

        ServerSocketChannel serverSocketChannel;

        Server.service = Executors.newCachedThreadPool();

        try {

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);

            System.out.println("Сервер запустился...");

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                session.add(socketChannel);
                System.out.println("Клиент подключился: " + socketChannel.getRemoteAddress());

                ServerThread serverThread = new ServerThread(socketChannel);

                service.execute(serverThread);
            }



        } catch (IOException e) {
            e.printStackTrace();
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

    public static synchronized PriorityQueue<LabWork> getPriorityQueue() {
        return priorityQueue;
    }

    public static synchronized DataBase getDataBase() {
        return dataBase;
    }

    public static synchronized Set<SocketChannel> getSession() {
        return session;
    }

}

