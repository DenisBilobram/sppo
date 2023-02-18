package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandHead implements Command{

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        LabWork lab = colleStack.peek();
        System.out.println(lab);
        return false;
    }
    
}
