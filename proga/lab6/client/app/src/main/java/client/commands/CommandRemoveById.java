package client.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import client.labwork.LabWork;


/** Класс команды реализующей удаление элемента из коллекции по его id.
 * 
 */
public class CommandRemoveById extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        Iterator<LabWork> iter = priorityQueue.iterator();
        LabWork labWork = null;
        while (iter.hasNext()) {
            labWork = iter.next();
            if (labWork.getId().equals(Long.parseLong((String)operands.get(0)))) {
                break;
            }
            labWork = null;
        }
        if (labWork == null) {
            System.out.println("Элемента с таким id не найдено.");
        } else {
            priorityQueue.remove(labWork);
            System.out.println("Элемента удалён.");
        }
    }
    
}

