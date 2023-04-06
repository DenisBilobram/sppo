package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        // TODO Auto-generated method stub
        PriorityQueue<LabWork> priorityQueueSorted = new PriorityQueue<>((s1,s2) -> s2.getName().length() - s1.getName().length());
        for (LabWork lab : priorityQueue) {
            priorityQueueSorted.add(lab);
        }
        System.out.println(priorityQueueSorted.peek());
    }
    
}
