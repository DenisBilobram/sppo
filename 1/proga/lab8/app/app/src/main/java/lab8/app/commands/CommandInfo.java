package lab8.app.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей отображение информации о коллекции.
 * 
 */
public class CommandInfo extends Command {


    public CommandInfo() {
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {
        Date min = new Date();
        for (LabWork labWork : priorityBlockingQueue) {
            if (labWork.getCreationDate().compareTo(min) < 0) {
                min = labWork.getCreationDate();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String minString = dateFormat.format(min);
        if (priorityBlockingQueue.size() == 0) {
            minString = "--:--:--";
        }

        ServerSignal resultSignal = new ServerSignal(bundle.getString("colinform") + ": \n" + bundle.getString("typecol") + ": " + priorityBlockingQueue.getClass().toString() + "\n" + bundle.getString("creatdata") + ": " + minString + "\n" + bundle.getString("elcount") + ": " + priorityBlockingQueue.size() + "\n");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
