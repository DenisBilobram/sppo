package lab6.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab6.app.signals.ClientSignal;


public class Sender {

    private SocketChannel channel;

    public Sender(SocketChannel channel) {
        this.channel = channel;
    }
    

    public boolean sendCommandSignal(ClientSignal signal, ServerConnection server) {
        try {

            byte [] data = SerializationUtils.serialize(signal);
            ByteBuffer byteData = ByteBuffer.wrap(data);

            if (!server.checkConnectiion()) {
                return false;
            }

            int numWrite = channel.write(byteData);

            if (numWrite == -1 || !server.checkConnectiion()) {
                return false;
            }
            
            return true;

        } catch (IOException exception) {
            return false;
        }
    }
    
}
