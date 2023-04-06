package server;


import java.util.List;

import javax.xml.crypto.Data;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress(7777));

        SocketChannel channel = serverChannel.accept();
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);
        buffer.flip();

        NewClass obj1 = (NewClass)SerializationUtils.deserialize(buffer.array());
        System.out.println(obj1.a);

        
    }
}
