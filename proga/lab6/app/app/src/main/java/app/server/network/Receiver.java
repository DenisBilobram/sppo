package app.server.network;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.SerializationUtils;

import app.commands.*;

import app.signals.CommandSignal;

/** Класс отвечающий за парсинг строковой комманды в объект.
 * 
 */
public class Receiver {
    
    InputStream inputStream;
    public static Long maxId = 0l;
    

    public Receiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Command recieveCommand() {

        byte[] data = new byte[1024 * 32];

        try {
            int i = inputStream.read(data);
            while (i == 0) {
                i = inputStream.read(data);
            }
            if (i == -1) {
                throw new IOException();
            }
            return ((CommandSignal)SerializationUtils.deserialize(data)).getCommand();
        } catch (IOException e) {
            return null;
        }

    }
}