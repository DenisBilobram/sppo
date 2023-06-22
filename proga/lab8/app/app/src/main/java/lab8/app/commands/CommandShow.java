package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        ServerSignal resultSignal = new ServerSignal();
        resultSignal.setPriorityBlockingQueue(priorityBlockingQueue);
        
        resultSignal.setSucces(true);
        return resultSignal;
    }

}
