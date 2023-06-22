package lab8.app.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;

/** Класс команды реализующей отображение информации о коллекции.
 * 
 */
public class CommandInfo extends Command {


    public CommandInfo() {
        this.description = "Команда Info отображает текущее состояние коллекции.";
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
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

        ServerSignal resultSignal = new ServerSignal(String.format("Информация о коллекции:\nТип: %s\nДата создания: %s\nКол-во эллементов: %d", priorityBlockingQueue.getClass().toString(), minString, priorityBlockingQueue.size()));
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
