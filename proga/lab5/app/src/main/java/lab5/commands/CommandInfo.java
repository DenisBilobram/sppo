package lab5.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandInfo implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        Date min = new Date();
        for (LabWork lab : colleStack) {
            if (lab.getCreationDate().compareTo(min) < 0) {
                min = lab.getCreationDate();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String minString = dateFormat.format(min);
        System.out.println(String.format("Информация о коллекции:\nТип: %s\nДата создания: %s\nКол-во эллементов: %d", colleStack.getClass().toString(), minString, colleStack.size()));
        return false;
    }
    
}
