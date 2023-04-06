package client.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import client.input.LabWorkInput;
import client.labwork.LabWork;

/** Класс команды реализующей удаление элемента в конце коллекции.
 * 
 */
public class CommandRemoveLower extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue) {
        LabWork userLabWork = LabWorkInput.getLabWork((Scanner)operands.get(0));
        Iterator<LabWork> iter = priorityQueue.iterator(); 
        LabWork labWork = null;
        while (iter.hasNext()) {
            labWork = iter.next();
            if (labWork.getTunedInWorks() == null) {
                continue;
            }
            if (labWork.getTunedInWorks() < userLabWork.getTunedInWorks()) {
                iter.remove();
            }
        }
        System.out.println("Элементы с полем ID меньше заданного были удалены.");
    }
    
}
