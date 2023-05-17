package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.labwork.LabWork;
import lab7.app.labwork.comparators.LabwWorkComparatorByName;
import lab7.app.signals.Signal;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        Signal resultSignal = new Signal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
            return resultSignal;
        } else {
            LabwWorkComparatorByName comparator = new LabwWorkComparatorByName();
            resultSignal.setMessage(priorityBlockingQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}
