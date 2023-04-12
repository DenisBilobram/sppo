package lab5.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

import lab5.labwork.LabWork;

/** Класс команды реализующей отображение информации о коллекции.
 * 
 */
public class CommandInfo implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        Date min = new Date();
        for (LabWork labWork : priorityQueue) {
            if (labWork.getCreationDate().compareTo(min) < 0) {
                min = labWork.getCreationDate();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String minString = dateFormat.format(min);
        System.out.println(String.format("\nИнформация о коллекции:\nТип: %s\nДата создания: %s\nКол-во эллементов: %d", priorityQueue.getClass().toString(), minString, priorityQueue.size()));
    }
    
}
