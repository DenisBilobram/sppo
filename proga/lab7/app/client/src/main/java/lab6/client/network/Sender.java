package lab7.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab7.app.signals.ClientSignal;


public class Sender {

    private SocketChannel channel;

    public Sender(SocketChannel channel) {
        this.channel = channel;
    }
    

    public boolean sendCommandSignal(ClientSignal signal, ServerConnection server) {
        try {

            byte [] data = SerializationUtils.serialize(signal);
            ByteBuffer byteData = ByteBuffer.wrap(data);

            int numWrite = channel.write(byteData);
            
            if (numWrite == -1) {
                throw new IOException();
            }
            
            return true;

        } catch (IOException exception) {
            return false;
        }
    }
    
}
