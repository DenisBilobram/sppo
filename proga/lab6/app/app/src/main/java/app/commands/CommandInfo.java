package app.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей отображение информации о коллекции.
 * 
 */
public class CommandInfo extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        Date min = new Date();
        for (LabWork labWork : priorityQueue) {
            if (labWork.getCreationDate().compareTo(min) < 0) {
                min = labWork.getCreationDate();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String minString = dateFormat.format(min);

        Signal resultSignal = new Signal(String.format("Информация о коллекции:\nТип: %s\nДата создания: %s\nКол-во эллементов: %d", priorityQueue.getClass().toString(), minString, priorityQueue.size()));
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
