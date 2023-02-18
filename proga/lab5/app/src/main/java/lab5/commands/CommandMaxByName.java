package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandMaxByName implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        // TODO Auto-generated method stub
        PriorityQueue<LabWork> qu = new PriorityQueue<>((s1,s2) -> s2.getName().length() - s1.getName().length());
        for (LabWork lab : colleStack) {
            qu.add(lab);
        }
        System.out.println(qu.peek());
        return false;
    }
    
}
