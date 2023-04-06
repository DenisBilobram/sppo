package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;

/** Класс команды реализующей отображение поля TunedInWorks у всех элементов коллекции в порядке убывания.
 * 
 */
public class CommandPrintTunedInWorks implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        PriorityQueue<LabWork> priorityQueueSorted = new PriorityQueue<>((s1,s2) -> s2.getTunedInWorks().intValue() - s1.getTunedInWorks().intValue());
        for (LabWork labWork : priorityQueue) {
            priorityQueueSorted.add(labWork);
        }
        while (!priorityQueueSorted.isEmpty()) {
            LabWork labWork = priorityQueueSorted.poll();
            System.out.println(String.format("LabWork Id: %d, hourse: %d", labWork.getId(), labWork.getTunedInWorks()));
        }
    }
}
