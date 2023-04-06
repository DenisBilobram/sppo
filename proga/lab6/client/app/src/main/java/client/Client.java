package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketPermission;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.apache.commons.lang3.SerializationUtils;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 7777));
        channel.configureBlocking(false);

        NewClass obj = new NewClass();
        obj.a = 1;
        
        byte [] data = SerializationUtils.serialize(obj);

        ByteBuffer byteData = ByteBuffer.wrap(data);

        System.out.println(byteData);

        channel.write(byteData);
        
    }
}

class NewClass implements Serializable {
    int a;
}
