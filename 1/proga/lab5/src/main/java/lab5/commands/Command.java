package lab5.commands;

import java.util.PriorityQueue;

import lab5.labwork.LabWork;


/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public interface Command {

    public abstract void execute(PriorityQueue<LabWork> PriorityQueue, Object operand);

}
