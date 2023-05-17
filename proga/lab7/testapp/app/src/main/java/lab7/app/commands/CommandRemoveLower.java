package lab7.app.commands;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей удаление элемента в конце коллекции.
 * 
 */
public class CommandRemoveLower extends Command {

    public CommandRemoveLower() {
        this.requireLabWork = true;
    }

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        LabWork userLabWork = labWorkUpdate;

        List<LabWork> labWorks = priorityBlockingQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).toList();

        for (LabWork labWork : labWorks) {

            boolean deleted = DataBase.deleteLabWorkById(labWork.getId());
            if (!deleted) {
                Signal resultSignal = new Signal("Не удалось удалить элементы по техническим причинам.");
                resultSignal.setSucces(false);
                return resultSignal;
            }
        }
        
        priorityBlockingQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).forEach(x -> priorityBlockingQueue.remove(x));

        Signal resultSignal = new Signal("Элементы с полем tunedInWorks меньше заданного, если таковые были, удалены.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
