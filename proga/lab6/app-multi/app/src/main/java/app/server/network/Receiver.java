package app.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.PriorityQueue;

import org.apache.commons.lang3.SerializationUtils;

import app.commands.*;
import app.labwork.LabWork;
import app.signals.ClientSignal;

/** Класс отвечающий за получение объектов от клиента по сети.
 * 
 */
public class Receiver {
    
    public static Long maxId = 0l;

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

            return ((ClientSignal)SerializationUtils.deserialize(data)).getCommand();
            
        } catch (IOException e) {
            return null;
        } 

    }

    public static void setMaxId(PriorityQueue<LabWork> priorityQueue) {
        Long maxLong = 0l;

        for (LabWork lab : priorityQueue) {
            if (lab.getId() > maxLong) {
                maxLong = lab.getId();
            }
        }

        Receiver.maxId = maxLong;
    }
}