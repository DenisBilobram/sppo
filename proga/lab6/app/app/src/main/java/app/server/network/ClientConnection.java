package app.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientConnection {

    private SocketChannel channel;
    private OutputStream outputStream;
    private InputStream inputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public static ServerSocketChannel openChannel(int host) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(host));
            return serverChannel;
        } catch (IOException e) {
            
            return null;
        }
    }

    public boolean connectToClient(ServerSocketChannel serverChannel) {
        try {

            this.channel = serverChannel.accept();
            
            this.outputStream = channel.socket().getOutputStream();
            this.inputStream = channel.socket().getInputStream();

            return true;

        } catch (IOException exception) {
            System.out.println("Не удалось подключиться к клиенту.");
            return false;
        }
    }

    public void disconnect() {
        try {
            this.channel.close();
        } catch (IOException e) {
            System.out.println("Не могу отключиться от клиента.");
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
