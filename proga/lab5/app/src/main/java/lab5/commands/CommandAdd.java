package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandAdd implements Command {

    private LabWork operand;

    public CommandAdd(LabWork operand) {
        this.operand = operand;
    }

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        colleStack.add(this.operand);
        return true;
    }
}
