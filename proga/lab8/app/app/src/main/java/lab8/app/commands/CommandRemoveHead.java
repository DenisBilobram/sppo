package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead extends Command {


    public CommandRemoveHead() {
        this.description = "Команда Remvoe head удаляет самый старый элемент коллекции.";
    }

    LabWork operand;
    int index;

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        ServerSignal resultSignal = new ServerSignal();

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
