package app.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import app.signals.Signal;

public class Reciever {

    private SocketChannel channel;
    
    public Reciever(SocketChannel channel) {
        this.channel = channel;
    }
    
    public Signal getServerSignal() {

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        int i = 0;
        while (i == 0) {
            try {
                i = this.channel.read(buffer);
            } catch (IOException e) {
                Signal serverSignal = new Signal("Не удалось получить ответ от сервера.");
                serverSignal.setSucces(false);
                return serverSignal;
            }
        }

        buffer.flip();
        
        Signal serverSignal = SerializationUtils.deserialize(buffer.array());
        return serverSignal;
    }

}
