package lab7.app.signals;

import java.net.SocketAddress;

import lab7.app.auth.User;

public class ServerSignal extends Signal {
    
    protected SocketAddress clientAdress;
    protected User user;

    public ServerSignal() {
        message = "";
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
