package lab6.app.commands;

import java.util.PriorityQueue;

import lab6.app.labwork.LabWork;
import lab6.app.labwork.comparators.LabwWorkComparatorByName;
import lab6.app.signals.Signal;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        Signal resultSignal = new Signal();

        if (priorityQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
            return resultSignal;
        } else {
            LabwWorkComparatorByName comparator = new LabwWorkComparatorByName();
            resultSignal.setMessage(priorityQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}
