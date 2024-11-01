package lab6.app.commands;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();

        if (priorityQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(true);
            return resultSignal;
        }

        String result = priorityQueue.stream().map(x -> x.toString() + "\n").collect(Collectors.joining("---------------------------------\n"));
        resultSignal.setMessage(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
