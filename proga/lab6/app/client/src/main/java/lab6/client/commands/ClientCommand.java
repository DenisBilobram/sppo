package lab6.client.commands;

import java.util.PriorityQueue;

import lab6.app.commands.Command;
import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;

public abstract class ClientCommand extends Command {

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        return new Signal("Not implemented");
    }

    public abstract Signal execute();
    
}
