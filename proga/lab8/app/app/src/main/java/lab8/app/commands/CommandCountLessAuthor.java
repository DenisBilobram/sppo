package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author_name меньше заданного. 
 * 
 */
public class CommandCountLessAuthor extends Command {

    public CommandCountLessAuthor() {
        this.requirePersonName = true;
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        String authorName = getPersonName();
        ServerSignal resultSignal = new ServerSignal();


        Long c = priorityBlockingQueue.stream().filter(x -> x.getAuthor().getName().length() < authorName.length()).count();
        resultSignal.setMessage(String.format(bundle.getString("count") + ": %d", c));
        resultSignal.setSucces(true);


        return resultSignal;
        
    }

}
