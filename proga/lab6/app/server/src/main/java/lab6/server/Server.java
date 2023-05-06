package lab6.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import lab6.server.network.Sender;
import lab6.app.commands.Command;
import lab6.app.labwork.LabWork;
import lab6.server.database.DataReader;
import lab6.server.database.Database;
import lab6.server.network.Receiver;
import lab6.app.signals.ServerSignal;
import lab6.app.signals.Signal;

public class Server {

    private static PriorityQueue<LabWork> priorityQueue;
    private static Database dataBase;
    private static Selector selector;
    private static InetSocketAddress address;
    private static Set<SocketChannel> session;
    private static Set<ServerSignal> signals;
    public static Long maxId;

    public static void main(String args[]) {

        if (args.length != 1 || !isPort(args[0])) {
            System.out.println("Неверный формат аргументов: {port}");
            System.exit(0);
        }

        address = new InetSocketAddress(Integer.parseInt(args[0]));
        session = new HashSet<SocketChannel>();
        signals = new HashSet<ServerSignal>();

        dataBase = new Database();
        Signal dbSignal = dataBase.connect();

        if (!dbSignal.isSucces()) {
            System.out.println(dbSignal.getMessage());
            System.exit(0);
        }
        DataReader dataReader = new DataReader(dataBase);

        priorityQueue = dataReader.read();
        
        setMaxId(priorityQueue);

        Thread consoleReader = new ConsoleReader();
        consoleReader.start();

        try {

            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер запустился...");

            while (true) {

                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while(keys.hasNext()) {

                    SelectionKey key = (SelectionKey) keys.next();
                    keys.remove();

                    if (!key.isValid()) continue;

                    if (key.isAcceptable()) accept(key);
                    else if (key.isReadable()) read(key);
                    else if (key.isWritable()) write(key);

                }
            }

        } catch (IOException exp) {
            System.out.println("Ошибка во время запуска сервера.");
        }

        

    }

    private static void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            session.add(channel);
            System.out.println("Клиент подключён.");
        } catch (IOException exp) {
            System.out.println("Не удалось подключить клиента.");
        }
    }

    private static void read(SelectionKey key) {
        try {

            SocketChannel channel = (SocketChannel) key.channel();

            List<Command> commands = Receiver.recieveCommand(channel);

            if (commands == null) {
                session.remove(channel);
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                key.cancel();
                return;
            }
            
            System.out.println("Получил команду.");

            ServerSignal serverSignal = new ServerSignal();

            if (commands.size() == 0) {
                
                serverSignal.setMessage("В скрипте не найдено валидных команд.");
                serverSignal.setSucces(false);

            } else {
                
                serverSignal = new ServerSignal(commands.get(0).execute(priorityQueue));
                commands.remove(0);

                if (commands.size() > 0) {
                    for (Command command : commands) {
                        serverSignal.setMessage(serverSignal.getMessage() + "\n" + command.execute(priorityQueue).getMessage());
                    }
                    serverSignal.setSucces(true);
                    serverSignal.setMessage(serverSignal.getMessage() + "\nСкрипт выполнен.");
                }
            }

            
            
            serverSignal.setClientAdress(channel.getRemoteAddress());
            
            signals.add(serverSignal);

            channel.register(selector, SelectionKey.OP_WRITE);

        } catch (IOException exp) {
            System.out.println("Не удалось получить команду.");
        }
    }

    private static void write(SelectionKey key) {
        try {

            SocketChannel channel = (SocketChannel) key.channel();

            SocketAddress clienAddress = channel.getRemoteAddress();

            ServerSignal serverSignal = signals.stream().filter(x -> x.getClientAdress().equals(clienAddress)).findFirst().get();
            signals.remove(serverSignal);

            boolean sended = Sender.sendSignal(serverSignal, channel);
            if (sended) {
                System.out.println("Отправил сигнал клиенту.");
            } else {
                session.remove(channel);
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                key.cancel();
                return;
            }

            channel.register(selector, SelectionKey.OP_READ);

        } catch (IOException exp) {
            System.out.println("Не удалось отправить сигнал клиенту.");
        }
    }

    public static void setMaxId(PriorityQueue<LabWork> priorityQueue) {
        Long maxLong = 0l;

        for (LabWork lab : priorityQueue) {
            if (lab.getId() > maxLong) {
                maxLong = lab.getId();
            }
        }

        Server.maxId = maxLong;
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

    public static PriorityQueue<LabWork> getPriorityQueue() {
        return priorityQueue;
    }

    public static void setPriorityQueue(PriorityQueue<LabWork> priorityQueue) {
        Server.priorityQueue = priorityQueue;
    }

    public static Database getDataBase() {
        return dataBase;
    }

    public static void setDataBase(Database dataBase) {
        Server.dataBase = dataBase;
    }

    public static Selector getSelector() {
        return selector;
    }

    public static void setSelector(Selector selector) {
        Server.selector = selector;
    }

}

class ConsoleReader extends Thread {
    ConsoleReader() {
        super();
    }

    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
        
            while (true) {
                String command = scanner.nextLine();
                if (command.equals("save")) {
                    Server.getDataBase().save(Server.getPriorityQueue());
                    System.out.println("Коллекция сохранена.");
                }
                if (command.equals("exit")) {
                    scanner.close();
                    for (SelectionKey key : Server.getSelector().selectedKeys()) {
                        ((SocketChannel)key.channel()).finishConnect();
                        key.channel().close();
                    }
                    System.exit(0);
                }
            
            }
        } catch (Exception exp) {
        }
        
    }
}