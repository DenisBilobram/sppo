package app.commands;

import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();

        String result = new String();
        for (LabWork lab : priorityQueue) {
            result += "---------------------------------\n";
            result += lab.toString() + "\n";
            result += "---------------------------------\n";
        }
        resultSignal.setMessage(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
