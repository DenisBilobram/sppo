package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;

/** Класс команды реализующей выход из программы. 
 * 
 */
public class CommandExit implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        System.exit(0);
    }

    
}
