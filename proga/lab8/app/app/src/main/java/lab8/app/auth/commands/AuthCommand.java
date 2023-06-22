package lab8.app.auth.commands;

import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.lang3.NotImplementedException;

import lab8.app.auth.User;
import lab8.app.commands.Command;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;


public abstract class AuthCommand extends Command {

    private User user;

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        throw new NotImplementedException();
    }

    public abstract Signal execute();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
