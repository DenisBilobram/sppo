package lab8.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.commands.Command;
import lab8.app.commands.CommandShow;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ClientSignal;
import lab8.app.signals.ServerSignal;
import lab8.client.ClientApp;


public class ServerConnection {

    public static SocketChannel channel;
    private static boolean isConnected;
    private static String host;
    private static int port;
    private static Sender sender;
    private static Reciever receiver;

    public Reciever getReceiver() {
        return receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public boolean isConnected() {
        return ServerConnection.isConnected;
    }

    public void setConnected(boolean isConnected) {
        ServerConnection.isConnected = isConnected;
    }


    public ServerConnection(String host, int port) {
        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            System.out.println("Пытаюсь подключиться к " + address.getAddress());
            ServerConnection.channel = SocketChannel.open(address);
            ServerConnection.receiver = new Reciever(channel);
            ServerConnection.sender = new Sender(channel);
            channel.configureBlocking(false);
            isConnected = true;
            ServerConnection.host = host;
            ServerConnection.port = port;
        } catch (Exception exception) {
            exception.getStackTrace();
            String errLabel = "Не удалось подключиться к серверу.";
            String errButton = "Повтор";
            ClientApp.createErrorStage(errLabel, errButton, event -> {
                ClientApp.getErrorStage().close();
                ClientApp.setServer(new ServerConnection(host, port));
            });
            ClientApp.getErrorStage().setOnCloseRequest(event -> {
                ClientApp.getPrimaryStage().close();
                ClientApp.getAuthStage().close();
            });
            isConnected = false;
        }
    }
    
    public static SocketChannel getChannel() {
        return channel;
    }

    public void disconnect() {
        try {
            channel.finishConnect();
            channel.close();
        } catch (IOException exp) {
            System.out.println("Не получилось отключиться от сервера.");
        }
    }

    public boolean checkConnectiion() {
        return isConnected && channel.isConnected();
    }


    public PriorityBlockingQueue<LabWork> getLabWorkCollection() {
        Command commandShow = new CommandShow();
        ServerConnection.sender.sendClientSignal(new ClientSignal(commandShow));
        ServerSignal serverSignal = ServerConnection.receiver.getServerSignal();
        if (serverSignal != null) {
            return serverSignal.getPriorityBlockingQueue();
        } else {
            ClientApp.setServer(new ServerConnection(host, port));
            return getLabWorkCollection();
        } 
    }

    public ServerSignal executeCommandOnServer(Command command) {
        ServerConnection.sender.sendClientSignal(new ClientSignal(command));
        ServerSignal serverSignal = ServerConnection.receiver.getServerSignal();
        if (serverSignal != null) {
            return serverSignal;
        } else {
            ClientApp.setServer(new ServerConnection(host, port));
            return executeCommandOnServer(command);
        } 
    }

}
