package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead implements Command {

    LabWork operand;
    int index;

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        priorityQueue.poll();
        System.out.println("Верхний элемент удалён.");
    }
}
