package lab5.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.labwork.LabWork;
import lab5.labwork.LabWorkInput;

/** Класс команды реализующей удаление элемента в конце коллекции.
 * 
 */
public class CommandRemoveLower implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
        LabWork userLabWork = LabWorkInput.getLabWork((Scanner)operand);
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
