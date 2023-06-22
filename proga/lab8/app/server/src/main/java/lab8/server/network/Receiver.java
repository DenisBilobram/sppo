package lab8.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.apache.commons.lang3.SerializationUtils;

import lab8.app.commands.*;
import lab8.app.signals.ClientSignal;

/** Класс отвечающий за получение объектов от клиента по сети.
 * 
 */
public class Receiver {

    SocketChannel channel;

    public Receiver(SocketChannel channel) {
        this.channel = channel;
    }

    public Command recieveCommands() {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*32);

        byte[] data;

        try {
            
            int numRead = channel.read(byteBuffer);
            byteBuffer.flip();

            if (numRead == -1) {
                return null;
            }

            data = byteBuffer.array();

            Command command = ((ClientSignal)SerializationUtils.deserialize(data)).getCommand();
        
            return command;
            
        } catch (IOException e) {
            return null;
        } 

    }

    
}