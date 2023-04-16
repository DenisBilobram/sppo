package lab6.server.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab6.app.signals.ServerSignal;


public class Sender {
    
    public static boolean sendSignal(ServerSignal signal, SocketChannel channel) {
        try {

            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(signal));

            while (buffer.hasRemaining()) {

                int numWrite = channel.write(buffer);

                if (numWrite == -1) {
                    return false;
                }

            }

            buffer = ByteBuffer.wrap((new String("END")).getBytes());
            channel.write(buffer);

            return true;

        } catch (IOException exception) {
            System.out.println("Не cмог отправить сигнал клиенту.");
            return false;
        }
    }
}
