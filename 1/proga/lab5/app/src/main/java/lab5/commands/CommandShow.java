package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;


/** Класс команды реализующей отображение всех элементов в коллекции.
 * 
 */
public class CommandShow implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        System.out.println("Коллекция:");
        for (LabWork lab : priorityQueue) {
            System.out.println("---------------------------------");
            System.out.println(lab);
            System.out.println("---------------------------------");
        }
    }

}
