package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandShow implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        for (LabWork lab : colleStack) {
            System.out.println("---------------------------------");
            System.out.println(lab);
            System.out.println("---------------------------------");
        }
        return false;
    }

}
