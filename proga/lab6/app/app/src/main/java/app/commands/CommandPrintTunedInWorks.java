package app.commands;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс команды реализующей отображение поля TunedInWorks у всех элементов коллекции в порядке убывания.
 * 
 */
public class CommandPrintTunedInWorks extends Command {

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {
        

        String result = priorityQueue.stream().sorted(new Comparator<LabWork>() {

            public int compare(LabWork arg0, LabWork arg1) {
                return arg1.getTunedInWorks().intValue() - arg0.getTunedInWorks().intValue();
            }
            
        }).map(x -> String.format("LabWork Id: %d, hourse: %d", x.getId(), x.getTunedInWorks())).collect(Collectors.joining("\n"));

        Signal resultSignal = new Signal(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }
}
