package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;
import lab5.recivers.Reciever;

/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear implements Command{

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        priorityQueue.clear();
        System.out.println("Коллекция была очищена.");
        Reciever.maxId = 0l;
    }

}
