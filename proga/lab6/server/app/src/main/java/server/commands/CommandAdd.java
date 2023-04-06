package server.commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import server.input.LabWorkInput;
import server.labwork.LabWork;
import server.network.Receiver;

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
        Receiver.maxId += 1l;
    }
}
