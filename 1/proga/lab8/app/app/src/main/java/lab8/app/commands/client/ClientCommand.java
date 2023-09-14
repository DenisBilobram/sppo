package lab8.app.commands.client;

import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.lang3.NotImplementedException;

import lab8.app.commands.Command;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;

public abstract class ClientCommand extends Command {

    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        throw new NotImplementedException();
    }

    public abstract Signal execute();
    
}
