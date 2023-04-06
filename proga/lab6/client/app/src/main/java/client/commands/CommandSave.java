package client.commands;

import java.util.PriorityQueue;

import client.database.DataWriter;
import client.database.Database;
import client.labwork.LabWork;

/** Класс команды реализующей сохранение коллекции в базу данных.
 * 
 */
public class CommandSave extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        Database db = new Database();
        DataWriter dw = new DataWriter(db);
        dw.write(priorityQueue);
        System.out.println("Коллекция сохранена.");
    }

}
