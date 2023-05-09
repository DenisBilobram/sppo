package lab7.app.commands;

import java.util.PriorityQueue;

import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd extends Command {


    public CommandAdd() {
        this.requireLabWork = true;
    }

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        LabWork labWork = getLabWork();
        Signal resultSignal = new Signal();

        PriorityQueue.add(labWork);
        resultSignal.setMessage("Элемент был добавлен в коллекцию.");
        resultSignal.setSucces(true);
        
        return resultSignal;
    }
}
