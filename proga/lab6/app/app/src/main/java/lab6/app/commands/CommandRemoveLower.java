package lab6.app.commands;

import java.util.PriorityQueue;

import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;

/** Класс команды реализующей удаление элемента в конце коллекции.
 * 
 */
public class CommandRemoveLower extends Command {

    public CommandRemoveLower() {
        this.requireLabWork = true;
    }

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        LabWork userLabWork = labWork;
        
        priorityQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).forEach(x -> priorityQueue.remove(x));

        Signal resultSignal = new Signal("Элементы с полем tunedInWorks меньше заданного, если таковые были, удалены.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
