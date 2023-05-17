package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import lab7.server.Server;
public class AcceptClientAction extends RecursiveAction{

    private ServerSocketChannel serverSocketChannel;

    public AcceptClientAction(SelectionKey key) {
        this.serverSocketChannel = (ServerSocketChannel)key.channel();
        key.cancel();
    }

    @Override
    protected void compute() {
        
        try {

            SocketChannel socketChannel = serverSocketChannel.accept();
            
            socketChannel.configureBlocking(false);

            System.out.println("Подключился клиент " + socketChannel.getRemoteAddress());

            Server.getSession().add(socketChannel);

            socketChannel.register(Server.getSelector(), SelectionKey.OP_READ);
            System.out.println("Добавил ключ на чтение.");

            serverSocketChannel.register(Server.getSelector(), SelectionKey.OP_ACCEPT);
            

            


        } catch (IOException e) {

            System.out.println("Ошибка во время подключения клиента.");
            e.printStackTrace();
        }

    }

}
