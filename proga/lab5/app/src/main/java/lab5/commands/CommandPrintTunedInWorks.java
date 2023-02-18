package lab5.commands;

import java.util.PriorityQueue;

import lab5.LabWork;

public class CommandPrintTunedInWorks implements Command {

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        PriorityQueue<LabWork> qu = new PriorityQueue<>((s1,s2) -> (int)s2.getTunedInWorks() - (int)s1.getTunedInWorks());
        for (LabWork lab : colleStack) {
            qu.add(lab);
        }
        while (!qu.isEmpty()) {
            LabWork lab = qu.poll();
            System.out.println(String.format("LabWork Id: %d, hourse: %d", lab.getId(), lab.getTunedInWorks()));
        }
        return false;
    }
}
