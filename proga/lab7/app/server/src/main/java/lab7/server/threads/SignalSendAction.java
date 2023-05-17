package lab7.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;


import lab7.app.signals.ServerSignal;
import lab7.server.Server;
import lab7.server.network.Receiver;
import lab7.server.network.Sender;

public class SignalSendAction extends RecursiveAction{

    SocketChannel channel;
    Receiver receiver;
    Sender sender;
    
    ServerSignal signal;


    public SignalSendAction(SelectionKey key, ServerSignal signal) {
        this.channel = (SocketChannel)key.channel();
        key.cancel();
        this.receiver = new Receiver(channel);
        this.sender = new Sender(channel);
        this.signal = signal;
    }

    @Override
    protected void compute() {
        boolean sended = sender.sendSignal(signal);


        try {

            if (sended) {
                System.out.println("Отправил сигнал клиенту.");

                channel.register(Server.getSelector(), SelectionKey.OP_READ);
            } else {
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                Server.getSession().remove(channel);
                return;
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
    
}
