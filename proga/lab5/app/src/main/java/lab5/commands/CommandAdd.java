package lab5.commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.labwork.LabWork;
import lab5.labwork.LabWorkInput;
import lab5.recivers.Reciever;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        Scanner scanner = (Scanner)operand;
        LabWork labWork = LabWorkInput.getLabWork(scanner);
        if (labWork == null) {
            System.out.println("\nЭлемент не был добавлен.");
            return;
        }
        PriorityQueue.add(labWork);
        System.out.println("Элемент был добавлен в коллекцию.");
        Reciever.maxId += 1l;
    }
}
