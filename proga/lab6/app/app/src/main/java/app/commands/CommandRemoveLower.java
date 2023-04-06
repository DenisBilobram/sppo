package app.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей удаление элемента в конце коллекции.
 * 
 */
public class CommandRemoveLower extends Command {

    public CommandRemoveLower() {
        this.requireLabWork = true;
    }

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        LabWork userLabWork = labWork;
        Iterator<LabWork> iter = priorityQueue.iterator(); 
        LabWork labWork = null;
        while (iter.hasNext()) {
            labWork = iter.next();
            if (labWork.getTunedInWorks() == null) {
                continue;
            }
            if (labWork.getTunedInWorks() < userLabWork.getTunedInWorks()) {
                iter.remove();
            }
        }
        Signal resultSignal = new Signal("Элементы с полем ID меньше заданного, если таковые были, удалены.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
