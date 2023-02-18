package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandHelp implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        System.out.println("Команды: add {element}, clear, execute script {file name}, exite, count less than {author}, " + 
                           "info, head, remove {id}, maxbyname, save, show, remove head, remove lower, " +
                           "TunedIdWorks fields, update {id} {element}");
        return true;
    }
    
}
