package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.labwork.comparators.LabwWorkComparatorByName;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    public CommandMaxByName() {
        this.description = "Команда Max by name отображает элемент с максимальным полем name.";
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        ServerSignal resultSignal = new ServerSignal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(false);
            return resultSignal;
        } else {
            LabwWorkComparatorByName comparator = new LabwWorkComparatorByName();
            resultSignal.setMessage(priorityBlockingQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}
