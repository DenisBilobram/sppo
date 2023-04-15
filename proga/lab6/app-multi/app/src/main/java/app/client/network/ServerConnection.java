package app.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ServerConnection {

    public static SocketChannel channel;
    
    public static SocketChannel getChannel() {
        return channel;
    }

    public boolean coonectToServer(String host, int port) {
        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            System.out.printf(host, port);
            System.out.println("Пытаюсь подключиться к " + address.getAddress());
            ServerConnection.channel = SocketChannel.open(address);
            channel.configureBlocking(false);
            return true;
        } catch (IOException exception) {
            System.out.println("Не удалось подключиться к серверу.");
            return false;
        }
    }

    public boolean checkConnectiion() {
        if (channel.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
