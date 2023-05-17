package lab7.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab7.app.signals.ClientSignal;
import lab7.client.Client;


public class Sender {

    private SocketChannel channel;

    public Sender(SocketChannel channel) {
        this.channel = channel;
    }
    

    public boolean sendClientSignal(ClientSignal signal) {
        try {

            byte [] data = SerializationUtils.serialize(signal);
            ByteBuffer byteData = ByteBuffer.wrap(data);


            int numWrite = channel.write(byteData);
            
            if (numWrite == -1) {
                throw new IOException();
            }
            
            return true;

        } catch (IOException exception) {
            Client.getServer().setConnected(false);
            return false;
        }
    }
    
}
