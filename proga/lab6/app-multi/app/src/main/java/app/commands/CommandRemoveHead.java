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

        Signal resultSignal = new Signal();

        if (priorityQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
            return resultSignal;
        }
        
        priorityQueue.poll();
        resultSignal.setMessage("Верхний элемент удалён.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
}
