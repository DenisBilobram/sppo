package app.commands.client;

import java.util.PriorityQueue;

import app.commands.Command;
import app.labwork.LabWork;
import app.signals.Signal;

public abstract class ClientCommand extends Command {

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        return new Signal("Not implemented");
    }

    public abstract Signal execute();
    
}
