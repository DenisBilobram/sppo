package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;


/** Класс команды реализующей удаление элемента из коллекции по его id.
 * 
 */
public class CommandRemoveById extends Command {

    public CommandRemoveById() {
        this.requireId = true;
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();
        LabWork labWorkToDelete = priorityBlockingQueue.stream().filter(labWork -> labWork.getId().equals(Long.valueOf(getId()))).findFirst().orElse(null);

        if (labWorkToDelete == null) {
            resultSignal.setMessage(bundle.getString("elnotfound"));
            resultSignal.setSucces(false);
        } else {
            if (labWorkToDelete.getOwner().getId() != getUser().getId()) {
                resultSignal.setMessage(bundle.getString("notowner"));
                resultSignal.setSucces(false);
                return resultSignal;
            }

            boolean deleted = DataBase.deleteLabWorkById(labWorkToDelete.getId());
            if (deleted) {
                priorityBlockingQueue.remove(labWorkToDelete);
                resultSignal.setMessage(bundle.getString("eldel"));
                resultSignal.setSucces(true);
            } else {
                resultSignal.setMessage(bundle.getString("elnotdel"));
                resultSignal.setSucces(false);
            }
            
        }
        return resultSignal;
    }
    
}

