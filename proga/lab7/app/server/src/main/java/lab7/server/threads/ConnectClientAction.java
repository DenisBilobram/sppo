package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import lab7.server.Server;
public class ConnectClientAction extends RecursiveAction{

    private ServerSocketChannel serverSocketChannel;

    public ConnectClientAction(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    protected void compute() {
        
        try {

            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("Подключился клиент " + socketChannel.getRemoteAddress());

            Server.createConnectionTask(serverSocketChannel);

            Server.createReceiveTask(socketChannel);


        } catch (IOException e) {

            System.out.println("Ошибка во время подключения клиента.");
            e.printStackTrace();
        }

    }

}
