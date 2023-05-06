package lab6.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ServerConnection {

    public static SocketChannel channel;
    private boolean isConnected;
    private String host;
    private int port;

    public boolean isConnected() {
        return isConnected;
    }

    public ServerConnection(String host, int port) {
        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            System.out.println("Пытаюсь подключиться к " + address.getAddress());
            ServerConnection.channel = SocketChannel.open(address);
            channel.configureBlocking(false);
            isConnected = true;
            this.host = host;
            this.port = port;
        } catch (Exception exception) {
            System.out.println("Не удалось подключиться к серверу.");
            isConnected = false;
        }
    }
    
    public static SocketChannel getChannel() {
        return channel;
    }

    public ServerConnection reconnect() {
        ServerConnection serverConnection = new ServerConnection(host, port);
        while (!serverConnection.checkConnectiion()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverConnection = new ServerConnection(host, port);
        }
        System.out.println("Переподключился.\n");
        return serverConnection;

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
        if (isConnected && channel.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
