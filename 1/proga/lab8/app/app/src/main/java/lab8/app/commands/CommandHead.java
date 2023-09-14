package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.labwork.comparators.LabWorkComparatorById;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей отображение элемента в начале коллекции. 
 * 
 */
public class CommandHead extends Command{

    public CommandHead() {
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {
        ServerSignal resultSignal = new ServerSignal();
        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage(bundle.getString("colempty"));
            resultSignal.setSucces(true);
        } else {
            LabWorkComparatorById comparator = new LabWorkComparatorById();
            resultSignal.setMessage(priorityBlockingQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }

        return resultSignal;
    }
    
}
