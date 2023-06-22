package lab8.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

import lab8.server.Server;
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

            serverSocketChannel.register(Server.getSelector(), SelectionKey.OP_ACCEPT);

            socketChannel.register(Server.getSelector(), SelectionKey.OP_READ);
            
            Server.getSelector().wakeup();
            


        } catch (IOException e) {

            System.out.println("Ошибка во время подключения клиента.");
            e.printStackTrace();
        }

    }

}
