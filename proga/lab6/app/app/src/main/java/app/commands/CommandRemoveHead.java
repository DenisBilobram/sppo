package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead extends Command {

    LabWork operand;
    int index;

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        
        priorityQueue.poll();
        Signal resultSignal = new Signal("Верхний элемент удалён.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
}
