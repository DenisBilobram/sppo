package lab6.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import lab6.app.signals.Signal;

public class Reciever {

    private SocketChannel channel;
    
    public Reciever(SocketChannel channel) {
        this.channel = channel;
    }
    
    public Signal getServerSignal() {

        Signal serverSignal;

        int size = 1024;

        ByteBuffer byteBuffer = ByteBuffer.allocate(size);


        try {
            byte[] byteComm = new byte[13];
            int numRead = 0;
            
            while(true) {

                if (numRead >= 13) {

                    byteBuffer.get(byteBuffer.position()-13, byteComm);
                    
                    String comm = new String(byteComm);

                    if (comm.equals("END8374857392")) {
                        byteBuffer = byteBuffer.slice(0, byteBuffer.position()-3);
                        break;
                    }
                }
                    
                if (!byteBuffer.hasRemaining()) {
                    ByteBuffer byteBufferOld = byteBuffer;
                    size *= 2;
                    byteBuffer = ByteBuffer.allocate(size);
                    byteBuffer.put(byteBufferOld.array());
                }
                numRead = channel.read(byteBuffer);
                if (numRead == -1) {
                    return null;
                }
            }
            byteBuffer.flip();

            serverSignal = SerializationUtils.deserialize(byteBuffer.array());
            return serverSignal;
            
        } catch (IOException e) {
            return null;
        } 
        
        
    }

}
