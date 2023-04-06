package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;
import server.network.Receiver;

/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear implements Command{

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        priorityQueue.clear();
        System.out.println("Коллекция была очищена.");
        Receiver.maxId = 0l;
    }

}
