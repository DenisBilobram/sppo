package lab5.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import lab5.LabWork;

public class CommandUpdate implements Command {

    LabWork operand;

    Long changId;
    

    public CommandUpdate(LabWork operand, Long changId) {
        this.operand = operand;
        this.changId = changId;
    }


    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        Iterator<LabWork> iter = colleStack.iterator();
        while(iter.hasNext()) {
            LabWork lab = iter.next();
            if (lab.getId().equals(changId)) {
                lab.setName(this.operand.getName());
                lab.setCoordinates(this.operand.getCoordinates());
                lab.setDifficulty(this.operand.getDifficulty());
                lab.setMinimalPoint(this.operand.getMinimalPoint());
                lab.setTunedInWorks(this.operand.getTunedInWorks());
                lab.setAuthor(this.operand.getAuthor());
                lab.setCreationDate(this.operand.getCreationDate());
                return true;
            }
        }
        System.out.println("Элемент с таким id не найден.");
        return false;
    }
    
}