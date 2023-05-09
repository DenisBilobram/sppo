package lab7.app.commands;

import java.util.PriorityQueue;

import lab7.app.labwork.LabWork;
import lab7.app.labwork.comparators.LabWorkComparatorById;
import lab7.app.signals.Signal;

/** Класс команды реализующей отображение элемента в начале коллекции. 
 * 
 */
public class CommandHead extends Command{

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        Signal resultSignal = new Signal();
        if (priorityQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(true);
        } else {
            LabWorkComparatorById comparator = new LabWorkComparatorById();
            resultSignal.setMessage(priorityQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }

        return resultSignal;
    }
    
}
