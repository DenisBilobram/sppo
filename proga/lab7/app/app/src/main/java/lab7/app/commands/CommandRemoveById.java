package lab7.app.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;


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

            boolean deleted = DataBase.deleteLabWorkById(labWork.getId());
            if (deleted) {
                priorityQueue.remove(labWork);
                resultSignal.setMessage("Элемент удалён.");
                resultSignal.setSucces(true);
            } else {
                resultSignal.setMessage("Не удалось удалить элемент по техническим причинам.");
                resultSignal.setSucces(false);
            }
            
        }
        return resultSignal;
    }
    
}

