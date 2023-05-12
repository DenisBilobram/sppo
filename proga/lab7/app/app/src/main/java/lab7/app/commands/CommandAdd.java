package lab7.app.commands;

import java.util.PriorityQueue;

import lab7.app.auth.User;
import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd extends Command {


    public CommandAdd() {
        this.requireLabWork = true;
    }

    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        
        LabWork labWork = getLabWorkUpdate();
        User user = getUser();


        Signal resultSignal = new Signal();

        LabWork createdLabWork = DataBase.createLabWork(labWork, user);
        if (createdLabWork != null) {
            PriorityQueue.add(labWork);
            resultSignal.setMessage("Элемент был добавлен в коллекцию.");
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage("Элемент не был добавлен в коллекцию по техническим причинам.");
            resultSignal.setSucces(false);
        }
        
        return resultSignal;
    }
}
