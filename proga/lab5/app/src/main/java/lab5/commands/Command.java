package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public interface Command {

    public abstract boolean execute(PriorityQueue<LabWork> colleStack);

}
