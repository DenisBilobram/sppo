package lab7.app.auth.commands;

import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.lang3.NotImplementedException;

import lab7.app.auth.User;
import lab7.app.commands.Command;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;


public abstract class AuthCommand extends Command {

    private User user;

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
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
