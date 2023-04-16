package lab6.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab6.app.commands.*;
import lab6.app.signals.ClientSignal;
import lab6.server.Server;

/** Класс отвечающий за получение объектов от клиента по сети.
 * 
 */
public class Receiver {

    public static Command recieveCommand(SocketChannel channel) {

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

            if (command instanceof CommandAdd) {
                Server.maxId += 1;
                command.getLabWork().setId(Server.maxId);
            } else if (command instanceof CommandClear) {
                Server.maxId = 0l;
            }

            return command;
            
        } catch (IOException e) {
            return null;
        } 

    }

    
}