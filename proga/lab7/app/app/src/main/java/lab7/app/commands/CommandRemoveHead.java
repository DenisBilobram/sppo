package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead extends Command {

    LabWork operand;
    int index;

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        Signal resultSignal = new Signal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
            return resultSignal;
        }
        
        LabWork labWorkToDelete = priorityBlockingQueue.peek();

        boolean deleted = DataBase.deleteLabWorkById(labWorkToDelete.getId());
        if (deleted) {
            priorityBlockingQueue.poll();
            resultSignal.setMessage("Верхний элемент удалён.");
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage("Верхний элемент не удалён по техническим причинам.");
            resultSignal.setSucces(false);
        }

        
        return resultSignal;
    }
}
