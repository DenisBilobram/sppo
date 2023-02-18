package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandRemoveHead implements Command {

    LabWork operand;
    int index;

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        colleStack.poll();
        return true;
    }
}
