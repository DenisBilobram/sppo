package lab7.app.commands.client;

import java.util.PriorityQueue;

import org.apache.commons.lang3.NotImplementedException;

import lab7.app.commands.Command;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

public abstract class ClientCommand extends Command {

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        throw new NotImplementedException();
    }

    public abstract Signal execute();
    
}
