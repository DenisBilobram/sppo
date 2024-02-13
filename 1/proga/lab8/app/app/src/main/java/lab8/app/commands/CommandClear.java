package lab8.app.commands;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;


/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{
    
    public CommandClear() {
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();

        List<LabWork> labWorksToDelete = priorityBlockingQueue.stream().filter(labWork -> labWork.getOwner().getId() == getUser().getId()).toList();
        if (labWorksToDelete.size() == 0) {
            resultSignal.setMessage(bundle.getString("clearer1"));
            resultSignal.setSucces(false);
            return resultSignal;
        }

        boolean cleared = DataBase.deleteAllLabWorks();
        if (cleared) {
            priorityBlockingQueue.clear();
            resultSignal.setMessage(bundle.getString("clearsuc"));
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage(bundle.getString("clearer"));
            resultSignal.setSucces(false);
        }

        return resultSignal;
    }

}
