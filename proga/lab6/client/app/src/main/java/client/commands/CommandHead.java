package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;

/** Класс команды реализующей отображение элемента в начале коллекции. 
 * 
 */
public class CommandHead extends Command{

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        LabWork labWork = priorityQueue.peek();
        System.out.println(labWork);
    }
    
}
