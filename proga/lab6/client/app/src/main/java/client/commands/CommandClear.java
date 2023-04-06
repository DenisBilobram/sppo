package client.commands;

import java.util.PriorityQueue;

import client.input.CommandParser;
import client.labwork.LabWork;

/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        priorityQueue.clear();
        System.out.println("Коллекция была очищена.");
    }

}
