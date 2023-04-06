package app.server.network;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.SerializationUtils;

import app.signals.Signal;

public class Sender {

    OutputStream outputStream;

    public Sender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    public boolean sendSignal(Signal signal) {
        try {
            System.out.println("Отправляю клиенту.");
            outputStream.write(SerializationUtils.serialize(signal));
            System.out.println("Отправил.");
            return true;

        } catch (IOException exception) {
            System.out.println("Не могу отправить сигнал клиенту.");
            return false;
        }
    }
}
