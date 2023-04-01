package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;

/** Класс команды реализующей отображение элемента в начале коллекции. 
 * 
 */
public class CommandHead implements Command{

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        LabWork labWork = priorityQueue.peek();
        System.out.println(labWork);
    }
    
}
