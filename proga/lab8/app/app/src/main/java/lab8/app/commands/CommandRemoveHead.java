package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead extends Command {


    public CommandRemoveHead() {
    }

    LabWork operand;
    int index;

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage(bundle.getString("colempty"));
            resultSignal.setSucces(false);
            return resultSignal;
        }
        
        LabWork labWorkToDelete = priorityBlockingQueue.peek();

        if (labWorkToDelete.getOwner().getId() != getUser().getId()) {
            resultSignal.setMessage(bundle.getString("notowner"));
            resultSignal.setSucces(false);
            return resultSignal;
        }

        boolean deleted = DataBase.deleteLabWorkById(labWorkToDelete.getId());
        if (deleted) {
            priorityBlockingQueue.poll();
            resultSignal.setMessage(bundle.getString("headdel"));
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage(bundle.getString("elnotdel"));
            resultSignal.setSucces(false);
        }

        
        return resultSignal;
    }
}
