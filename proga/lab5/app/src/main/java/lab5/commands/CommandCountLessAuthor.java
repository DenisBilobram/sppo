package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;
import lab5.Person;

public class CommandCountLessAuthor implements Command {

    Person operand;

    public CommandCountLessAuthor(Person operand) {
        this.operand = operand;
    }

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        int c = 0;
        for (LabWork lab : colleStack) {
            if (lab.getAuthor().getName().length() < operand.getName().length()) {
                c += 1;
            }
        }
        System.out.println(String.format("Количество: %d", c));
        return true;
    }

}
