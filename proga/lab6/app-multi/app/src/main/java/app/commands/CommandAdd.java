package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.server.network.Receiver;
import app.signals.Signal;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd extends Command {

    public CommandAdd() {
        this.requireLabWork = true;
    }

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {

        LabWork labWork = getLabWork();
        labWork.setId(Receiver.maxId + 1l);
        Signal resultSignal = new Signal();

        PriorityQueue.add(labWork);
        resultSignal.setMessage("Элемент был добавлен в коллекцию.");
        resultSignal.setSucces(true);
        Receiver.maxId += 1;
        
        return resultSignal;
    }
}
