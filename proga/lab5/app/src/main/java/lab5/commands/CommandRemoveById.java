package lab5.commands;

import java.util.Iterator;
import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandRemoveById implements Command {

    Long id;

    public CommandRemoveById(Long id) {
        this.id = id;
    }

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        Iterator<LabWork> iter = colleStack.iterator();
        LabWork lab = null;
        while (iter.hasNext()) {
            lab = iter.next();
            if (lab.getId().equals(this.id)) {
                break;
            } 
        }
        if (lab == null) {
            System.out.println("Элемента с таким id не найдено.");
        } else {
            colleStack.remove(lab);
        }
        
        return true;
    }
    
}

