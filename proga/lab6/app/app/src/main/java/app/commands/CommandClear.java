package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.server.network.Receiver;
import app.signals.Signal;


/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        priorityQueue.clear();
        Signal resultSignal = new Signal("Коллекция была очищена.");
        resultSignal.setSucces(true);
        Receiver.maxId = 0l;
        
        return resultSignal;
    }

}
