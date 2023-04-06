package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;

/** Класс команды реализующей выход из программы. 
 * 
 */
public class CommandExit implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        System.exit(0);
    }

    
}
