package app.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import app.server.network.Sender;
import app.commands.Command;
import app.labwork.LabWork;
import app.server.database.DataReader;
import app.server.database.Database;
import app.server.network.Receiver;
import app.signals.ServerSignal;
import app.signals.Signal;

public class Server {

    private PriorityQueue<LabWork> priorityQueue;
    private Selector selector;
    private InetSocketAddress address;
    private Set<SocketChannel> session;
    private Set<ServerSignal> signals;

    public Server(int port) {
        this.address = new InetSocketAddress(port);
        this.session = new HashSet<SocketChannel>();
        this.signals = new HashSet<ServerSignal>();
    }

    public void startServer() {

        Database dataBase = new Database();
        Signal dbSignal = dataBase.connect();

        if (!dbSignal.isSucces()) {
            System.out.println(dbSignal.getMessage());
            System.exit(0);
        }

        DataReader dataReader = new DataReader(dataBase);

        this.priorityQueue = dataReader.read();
        
        Receiver.setMaxId(priorityQueue);

        try {

            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

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

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(this.selector, SelectionKey.OP_READ);
            this.session.add(channel);
            System.out.println("Клиент подключён.");
        } catch (IOException exp) {
            System.out.println("Не удалось подключить клиента.");
        }
    }

    private void read(SelectionKey key) {
        try {

            SocketChannel channel = (SocketChannel) key.channel();

            Command command = Receiver.recieveCommand(channel);
            if (command == null) {
                this.session.remove(channel);
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                key.cancel();
                return;
            }
            
            System.out.println("Получил команду.");

            ServerSignal serverSignal = new ServerSignal(command.execute(priorityQueue));
            serverSignal.setClientAdress(channel.getRemoteAddress());
            
            signals.add(serverSignal);

            channel.register(this.selector, SelectionKey.OP_WRITE);

        } catch (IOException exp) {
            System.out.println("Не удалось получить команду.");
        }
    }

    private void write(SelectionKey key) {
        try {

            SocketChannel channel = (SocketChannel) key.channel();

            SocketAddress clienAddress = channel.getRemoteAddress();

            ServerSignal serverSignal = signals.stream().filter(x -> x.getClientAdress().equals(clienAddress)).findFirst().get();
            signals.remove(serverSignal);

            boolean sended = Sender.sendSignal(serverSignal, channel);
            if (sended) {
                System.out.println("Отправил сигнал клиенту.");
            } else {
                this.session.remove(channel);
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                key.cancel();
                return;
            }

            channel.register(this.selector, SelectionKey.OP_READ);

        } catch (IOException exp) {
            System.out.println("Не удалось отправить сигнал клиенту.");
        }
    }

}
