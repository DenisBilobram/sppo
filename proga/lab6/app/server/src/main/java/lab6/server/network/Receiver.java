package lab6.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import lab6.app.commands.*;
import lab6.app.signals.ClientSignal;
import lab6.server.Server;

/** Класс отвечающий за получение объектов от клиента по сети.
 * 
 */
public class Receiver {

    public static List<Command> recieveCommand(SocketChannel channel) {

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
            List<Command> commands;

            if (command instanceof CommandExecute) {
                commands = ((CommandExecute)command).getListOfCommands();
            } else {
                commands = new LinkedList<Command>(List.of(command));
            }
        
            for (Command innerCommand : commands) {
                if (innerCommand instanceof CommandAdd) {
                    Server.maxId += 1;
                    innerCommand.getLabWork().setId(Server.maxId);
                } else if (innerCommand instanceof CommandClear) {
                    Server.maxId = 0l;
                }
            }

            return commands;
            
        } catch (IOException e) {
            return null;
        } 

    }

    
}