package client.commands;

import java.io.Serializable;
import java.util.List;
import java.util.PriorityQueue;

import client.labwork.LabWork;


/** Интерфейс для реализации пользовательских команд в виде классов.
 * 
 */
public abstract class Command implements Serializable {

    protected List<Object> operands;

    protected boolean requireLab = false;

    public abstract void execute(PriorityQueue<LabWork> PriorityQueue);

    public void setOperands(List<Object> operands) {
        this.operands = operands;
    }

}
