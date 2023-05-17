package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        Signal resultSignal = new Signal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage("Коллекция пуста.");
            resultSignal.setSucces(true);
            return resultSignal;
        }

        String result = priorityBlockingQueue.stream().map(x -> x.toString() + "\nOwner username: " + DataBase.readUserNameByProfileId(x.getAuthor().getId()) + "\n").collect(Collectors.joining("---------------------------------\n"));
        resultSignal.setMessage(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
