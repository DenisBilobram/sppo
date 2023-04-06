package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей отображение поля TunedInWorks у всех элементов коллекции в порядке убывания.
 * 
 */
public class CommandPrintTunedInWorks extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        PriorityQueue<LabWork> priorityQueueSorted = new PriorityQueue<>((s1,s2) -> s2.getTunedInWorks().intValue() - s1.getTunedInWorks().intValue());
        for (LabWork labWork : priorityQueue) {
            priorityQueueSorted.add(labWork);
        }
        String result = new String();
        while (!priorityQueueSorted.isEmpty()) {
            LabWork labWork = priorityQueueSorted.poll();
            result += String.format("LabWork Id: %d, hourse: %d", labWork.getId(), labWork.getTunedInWorks());
        }
        Signal resultSignal = new Signal(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }
}
