package lab7.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import lab7.server.threads.CommandExecuteTask;
import lab7.server.threads.ConnectClientAction;
import lab7.server.threads.ConsoleReader;
import lab7.server.threads.SignalReceiveTask;
import lab7.server.threads.SignalSendAction;
import lab7.app.commands.Command;
import lab7.app.database.DataBase;
import lab7.app.database.DataSync;
import lab7.app.labwork.LabWork;
import lab7.app.signals.ServerSignal;

public class Server {

    private static volatile PriorityQueue<LabWork> priorityQueue;
    private static DataBase dataBase;
    private static Set<SocketChannel> session = new HashSet<SocketChannel>();
    private static ForkJoinPool pool = ForkJoinPool.commonPool();


    public static void main(String args[]) throws InterruptedException, IOException {

        if (args.length != 1 || !isPort(args[0])) {
            System.out.println("Неверный формат аргументов: {port}");
            System.exit(0);
        }
        
        Connection connection = DataBase.getDataBasConnection();

        Server.priorityQueue = DataSync.getQueueFromDataBase(connection);

        Thread consoleReader = new ConsoleReader();
        consoleReader.setDaemon(true);
        consoleReader.start();


        InetSocketAddress address = new InetSocketAddress(Integer.parseInt(args[0]));

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);

        createConnectionTask(serverSocketChannel);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
            }
        }

    }

    public static synchronized void createConnectionTask(ServerSocketChannel serverSocketChannel) {
        pool.execute(new ConnectClientAction(serverSocketChannel));
    }

    public static void createReceiveTask(SocketChannel channel) {
        pool.execute(new SignalReceiveTask(channel));
    }

    public static void createSendTask(SocketChannel channel, ServerSignal signal) {
        pool.execute(new SignalSendAction(channel, signal));
    }

    public static void createExecuteTask(Command command, SocketChannel channel) {
        pool.execute(new CommandExecuteTask(command, channel));
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

