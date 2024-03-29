package lab6.app.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;


/** Класс команды реализующей удаление элемента из коллекции по его id.
 * 
 */
public class CommandRemoveById extends Command {

    public CommandRemoveById() {
        this.requireId = true;
    }

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        Signal resultSignal = new Signal();
        Iterator<LabWork> iter = priorityQueue.iterator();
        LabWork labWork = null;
        while (iter.hasNext()) {
            labWork = iter.next();
            if (labWork.getId().equals(Long.parseLong(id))) {
                break;
            }
            labWork = null;
        }
        if (labWork == null) {
            resultSignal.setMessage("Элемента с таким id не найдено.");
            resultSignal.setSucces(false);
        } else {
            priorityQueue.remove(labWork);
            resultSignal.setMessage("Элемента удалён.");
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}

