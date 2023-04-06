package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;

/** Класс команды реализующей выход из программы. 
 * 
 */
public class CommandExit extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue) {
        System.exit(0);
    }

    
}
