package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();
        resultSignal.setPriorityBlockingQueue(priorityBlockingQueue);

        for (LabWork labWorkIn : priorityBlockingQueue) {
            resultSignal.setMessage(resultSignal.getMessage() + labWorkIn.toString());
        }

        resultSignal.setSucces(true);
        return resultSignal;
    }

}
