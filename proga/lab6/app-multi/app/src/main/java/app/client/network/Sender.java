package app.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import app.signals.ClientSignal;


public class Sender {

    private SocketChannel channel;

    public Sender(SocketChannel channel) {
        this.channel = channel;
    }
    

    public boolean sendCommandSignal(ClientSignal signal) {
        try {
            byte [] data = SerializationUtils.serialize(signal);
            ByteBuffer byteData = ByteBuffer.wrap(data);
            channel.write(byteData);
            return true;

        } catch (IOException exception) {
            return false;
        }
    }
    
}
