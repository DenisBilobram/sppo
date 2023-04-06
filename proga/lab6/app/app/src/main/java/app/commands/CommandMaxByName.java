package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        Signal resultSignal = new Signal();
        PriorityQueue<LabWork> priorityQueueSorted = new PriorityQueue<>((s1,s2) -> s2.getName().length() - s1.getName().length());
        for (LabWork lab : priorityQueue) {
            priorityQueueSorted.add(lab);
        }

        if (priorityQueueSorted.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
        } else {
            resultSignal.setMessage(priorityQueueSorted.peek().toString());
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}
