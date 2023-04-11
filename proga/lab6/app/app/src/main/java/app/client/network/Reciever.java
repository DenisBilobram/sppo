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

        Signal serverSignal;

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        int i = 0;
        while (i == 0) {
            try {
                i = this.channel.read(buffer);
            } catch (IOException e) {
                serverSignal = new Signal("Не удалось получить ответ от сервера.");
                serverSignal.setSucces(false);
                return serverSignal;
            }
        }

        
        if (i == -1) {

            int waitCounter = 0;
            System.out.println("Прервалось подключение к серверу.");

            while (i == -1) {
                waitCounter += 1;
                try {
                    System.out.println("Попытка чтения с сервера...");
                    Thread.sleep(1000);
                    i = this.channel.read(buffer);
                } catch (InterruptedException | IOException e) {
                    System.out.println("Ошибка при чтении с сервера.");
                }

                if (waitCounter > 2) {
                    serverSignal = new Signal("Прервалось подключение к серверу.");
                    serverSignal.setSucces(false);

                    try {
                        ServerConnection.channel.close();
                    } catch (IOException e) {
                        serverSignal.setMessage("Возникли проблемы при отключении от сервера.");
                    }
                    return serverSignal;
                }      
            }
        }

        buffer.flip();
        
        serverSignal = SerializationUtils.deserialize(buffer.array());
        return serverSignal;
    }

}
