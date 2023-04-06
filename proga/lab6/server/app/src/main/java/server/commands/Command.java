package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;


/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public interface Command {

    public abstract void execute(PriorityQueue<LabWork> PriorityQueue, Object operand);

}
