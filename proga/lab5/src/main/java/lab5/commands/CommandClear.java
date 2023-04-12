package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;

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
