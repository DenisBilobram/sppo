package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        System.out.println("Коллекция:");
        for (LabWork lab : priorityQueue) {
            System.out.println("---------------------------------");
            System.out.println(lab);
            System.out.println("---------------------------------");
        }
    }

}
