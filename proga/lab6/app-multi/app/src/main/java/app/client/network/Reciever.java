package app.client.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.SerializationUtils;

import app.signals.Signal;
import app.signals.SignalManager;
import app.signals.ServerSignal;

public class Reciever {

    private SocketChannel channel;
    
    public Reciever(SocketChannel channel) {
        this.channel = channel;
    }
    
    public Signal getServerSignal() {

        Signal serverSignal;

        int size = 1024;

        ByteBuffer byteBuffer = ByteBuffer.allocate(size);

        byte[] data;

        try {
            byte[] byteComm = new byte[3];
            int numRead = 0;
            
            while(true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (numRead >= 3) {
                    byteComm[0] = byteBuffer.get(byteBuffer.position()-3);
                    byteComm[1] = byteBuffer.get(byteBuffer.position()-2);
                    byteComm[2] = byteBuffer.get(byteBuffer.position()-1);
                    
                    String comm = new String(byteComm);

                    if (comm.equals("END")) {
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
                System.out.println(byteBuffer);
            }
            byteBuffer.flip();
            if (numRead == -1) {
                return null;
            }

            serverSignal = SerializationUtils.deserialize(byteBuffer.array());
            return serverSignal;
            
        } catch (IOException e) {
            return null;
        } 
        
        
    }

}
