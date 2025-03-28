package lab8.app.commands;

import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей отображение поля TunedInWorks у всех элементов коллекции в порядке убывания.
 * 
 */
public class CommandPrintTunedInWorks extends Command {

    public CommandPrintTunedInWorks() {
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {
        ServerSignal resultSignal = new ServerSignal();

        if (priorityBlockingQueue.size() == 0) {
            resultSignal.setMessage(bundle.getString("colempty"));
            resultSignal.setSucces(true);
            return resultSignal;
        }
        

        String result = priorityBlockingQueue.stream().sorted(new Comparator<LabWork>() {

            public int compare(LabWork arg0, LabWork arg1) {
                return arg1.getTunedInWorks().intValue() - arg0.getTunedInWorks().intValue();
            }
            
        }).map(x -> String.format("LabWork Id: %d, hourse: %d", x.getId(), x.getTunedInWorks())).collect(Collectors.joining("\n"));

        

        resultSignal.setMessage(result);
        resultSignal.setSucces(true);
        return resultSignal;
    }
}
