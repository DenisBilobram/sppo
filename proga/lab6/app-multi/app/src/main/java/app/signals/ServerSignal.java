package app.signals;

import java.net.SocketAddress;

public class ServerSignal extends Signal {
    
    protected SocketAddress clientAdress;

    public ServerSignal(Signal signal) {

        this.message = signal.getMessage();
        this.succes = signal.isSucces();

    }

    public SocketAddress getClientAdress() {
        return clientAdress;
    }

    public void setClientAdress(SocketAddress clientAdress) {
        this.clientAdress = clientAdress;
    }

}
