package app.commands;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

import app.labwork.LabWork;
import app.signals.Signal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();

        String result = priorityQueue.stream().map(x -> x.toString() + "\n").collect(Collectors.joining("---------------------------------\n"));
        resultSignal.setMessage(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
