package lab6.app.commands;

import java.util.PriorityQueue;

import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;


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
