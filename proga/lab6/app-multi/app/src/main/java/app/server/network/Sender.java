package app.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import app.signals.ServerSignal;


public class Sender {
    
    public static boolean sendSignal(ServerSignal signal, SocketChannel channel) {
        try {

            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(signal));

            int numWrite = channel.write(buffer);

            if (numWrite == -1) {
                return false;
            }

            return true;

        } catch (IOException exception) {
            System.out.println("Не cмог отправить сигнал клиенту.");
            return false;
        }
    }
}
