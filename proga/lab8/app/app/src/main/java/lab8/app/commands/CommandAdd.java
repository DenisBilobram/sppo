package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.auth.User;
import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd extends Command {

    public CommandAdd() {
        this.requireLabWork = true;
        this.description = "Команда Add добавляет новый элемент LabWork в коллекцию.";
    }

    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        
        LabWork labWork = getLabWorkNew();
        User user = getUser();
        labWork.setOwner(user);

        ServerSignal resultSignal = new ServerSignal();

        LabWork createdLabWork = DataBase.createLabWork(labWork, user);
        if (createdLabWork != null) {
            priorityBlockingQueue.add(labWork);
            resultSignal.setMessage("Элемент был добавлен в коллекцию.");
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage("Элемент не был добавлен в коллекцию по техническим причинам.");
            resultSignal.setSucces(false);
        }
        
        return resultSignal;
    }
}
