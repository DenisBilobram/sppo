package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandClear implements Command{

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        colleStack.clear();
        return true;
    }

}
