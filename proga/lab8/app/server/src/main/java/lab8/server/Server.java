package lab8.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import lab8.server.threads.AcceptClientAction;
import lab8.server.threads.ConsoleReader;
import lab8.server.threads.SignalReceiveTask;
import lab8.server.threads.SignalSendAction;
import lab8.app.database.DataBase;
import lab8.app.database.DataSync;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

public class Server {

    private static volatile  PriorityBlockingQueue<LabWork> priorityBlockingQueue;
    private static DataBase dataBase;
    private static Set<SocketChannel> session = new HashSet<SocketChannel>();
    private static HashMap<SocketAddress, ServerSignal> signals = new HashMap<>();

    private static ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-4);
    private static Executor executor = Executors.newFixedThreadPool(4);

    private static Selector selector;

    
    
    public static void main(String args[]) throws InterruptedException, IOException {

        if (args.length != 1 || !isPort(args[0])) {
            System.out.println("Неверный формат аргументов: {port}");
            System.exit(0);
        }
        
        Connection connection = DataBase.getDataBasConnection();

        Server.priorityBlockingQueue = DataSync.getQueueFromDataBase(connection);

        Thread consoleReader = new ConsoleReader();
        consoleReader.setDaemon(true);
        consoleReader.start();


        InetSocketAddress address = new InetSocketAddress(Integer.parseInt(args[0]));

        try {

            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер запустился...");
            
            while (true) {

                selector.select(5000);
                
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> keys = set.iterator();
                
                // System.out.println("------------");
                // selector.keys().stream().filter(x -> x.channel() instanceof ServerSocketChannel).forEach(x->System.out.println(x));;
                // System.out.println("------------");

                while(keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();
                    keys.remove();

                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) {
                        pool.execute(new AcceptClientAction(key));
                    } else if (key.isReadable()) {
                        pool.execute(new SignalReceiveTask(key));
                    } else if (key.isWritable()){
                        pool.execute(new SignalSendAction(key, signals.get(((SocketChannel)key.channel()).getRemoteAddress())));
                    }

                }
            }

            

        } catch (IOException exp) {
            
            System.out.println("Ошибка во время запуска сервера.");
            exp.printStackTrace();
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

    public static synchronized PriorityBlockingQueue<LabWork> getpriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

    public static synchronized DataBase getDataBase() {
        return dataBase;
    }

    public static synchronized Set<SocketChannel> getSession() {
        return session;
    }

    public static synchronized Selector getSelector() {
        return selector;
    }

    public static void setSelector(Selector selector) {
        Server.selector = selector;
    }

    public static synchronized void addSignal(ServerSignal signal) {
        signals.put(signal.getClientAdress(), signal);
    }

    public static Executor getExecutor() {
        return executor;
    }
}

