package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;
import lab5.database.DataWriter;
import lab5.database.Database;

public class CommandSave implements Command {

    Database operand;

    public CommandSave(Database operand) {
        this.operand = operand;
    }

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        DataWriter dw = new DataWriter(this.operand);
        dw.write(colleStack);
        return true;
    }

}
