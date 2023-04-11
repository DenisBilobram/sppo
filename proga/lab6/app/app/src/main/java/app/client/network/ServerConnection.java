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
            ServerConnection.channel = SocketChannel.open(new InetSocketAddress(host, port));
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
