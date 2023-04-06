package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;

/** Класс команды реализующей удаление элемента в начале коллекции.
 * 
 */
public class CommandRemoveHead extends Command {

    LabWork operand;
    int index;

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        priorityQueue.poll();
        System.out.println("Верхний элемент удалён.");
    }
}
