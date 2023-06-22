package lab8.app.commands;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;


/** Класс команды реализующей удаление элемента из коллекции по его id.
 * 
 */
public class CommandRemoveById extends Command {

    public CommandRemoveById() {
        this.description = "Команда Remove by ID удаляет элемент колекции по его ID.";
        this.requireId = true;
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        ServerSignal resultSignal = new ServerSignal();
        Iterator<LabWork> iter = priorityBlockingQueue.iterator();
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
                priorityBlockingQueue.remove(labWork);
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

