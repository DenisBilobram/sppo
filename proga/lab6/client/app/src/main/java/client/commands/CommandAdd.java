package client.commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import client.input.LabWorkInput;
import client.input.CommandParser;
import client.labwork.LabWork;

/** Класс команды реализующей добавление элемента в коллекцию.
 * 
 */
public class CommandAdd extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue) {
        Scanner scanner = (Scanner)operands.get(0);
        LabWork labWork = LabWorkInput.getLabWork(scanner);
        if (labWork == null) {
            System.out.println("\nЭлемент не был добавлен.");
            return;
        }
        PriorityQueue.add(labWork);
        System.out.println("Элемент был добавлен в коллекцию.");
    }
}
