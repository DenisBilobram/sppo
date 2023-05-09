package lab7.app.commands;

import java.util.PriorityQueue;

import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;


/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        priorityQueue.clear();
        Signal resultSignal = new Signal("Коллекция была очищена.");
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
