package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.labwork.LabWork;
import lab7.app.labwork.comparators.LabWorkComparatorById;
import lab7.app.signals.Signal;

/** Класс команды реализующей отображение элемента в начале коллекции. 
 * 
 */
public class CommandHead extends Command{

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        Signal resultSignal = new Signal();
        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(true);
        } else {
            LabWorkComparatorById comparator = new LabWorkComparatorById();
            resultSignal.setMessage(priorityBlockingQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }

        return resultSignal;
    }
    
}
