package lab5.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandRemoveLower implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        Iterator<LabWork> iter = colleStack.iterator(); 
        LabWork lab = null;
        while (iter.hasNext()) {
            lab = iter.next();
        }
        colleStack.remove(lab);
        return true;
    }
    
}
