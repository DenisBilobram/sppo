package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.labwork.comparators.LabwWorkComparatorByName;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей отображение элемента с максимальным именем.
 * 
 */
public class CommandMaxByName extends Command {

    public CommandMaxByName() {
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {
        ServerSignal resultSignal = new ServerSignal();
        System.out.println(bundle.getLocale().getLanguage());

        if (priorityBlockingQueue.size() == 0) {
            
            resultSignal.setMessage(bundle.getString("colempty"));
            resultSignal.setSucces(false);
            return resultSignal;
        } else {
            LabwWorkComparatorByName comparator = new LabwWorkComparatorByName();
            resultSignal.setMessage(priorityBlockingQueue.stream().max(comparator).get().toString());
            resultSignal.setSucces(true);
        }
        return resultSignal;
    }
    
}
