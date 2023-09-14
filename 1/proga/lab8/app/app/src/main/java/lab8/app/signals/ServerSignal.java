package lab8.app.signals;

import java.net.SocketAddress;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.auth.User;
import lab8.app.labwork.LabWork;

public class ServerSignal extends Signal {
    
    protected SocketAddress clientAdress;
    protected User user;
    protected PriorityBlockingQueue<LabWork> priorityBlockingQueue;

    public ServerSignal() {
        message = "";
    }

    public ServerSignal(String string) {
        message = string;
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

    public PriorityBlockingQueue<LabWork> getPriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

    public void setPriorityBlockingQueue(PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }

}
