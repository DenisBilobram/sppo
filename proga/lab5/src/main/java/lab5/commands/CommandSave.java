package lab5.commands;

import java.util.PriorityQueue;

import lab5.database.DataWriter;
import lab5.database.Database;
import lab5.labwork.LabWork;

/** Класс команды реализующей сохранение коллекции в базу данных.
 * 
 */
public class CommandSave implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        Database db = new Database();
        DataWriter dw = new DataWriter(db);
        dw.write(priorityQueue);
        System.out.println("Коллекция сохранена.");
    }

}
