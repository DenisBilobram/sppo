package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;
import lab8.app.labwork.LabWork;
import lab8.app.labwork.Person;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;

/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author_name меньше заданного. 
 * 
 */
public class CommandCountLessAuthor extends Command {

    public CommandCountLessAuthor() {
        this.requirePersonName = true;
        this.description = "Команда Count less auhtor считает количество элементов колекции, у которых поле author_name меньше заданного.";
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        String authorName = getPersonName();
        ServerSignal resultSignal = new ServerSignal();

        if (authorName == null) {

            resultSignal.setMessage("Команда не может быть выполнена.");
            resultSignal.setSucces(false);
            return resultSignal;

        } else {

            Long c = priorityBlockingQueue.stream().filter(x -> x.getAuthor().getName().length() < authorName.length()).count();
            resultSignal.setMessage(String.format("Количество: %d", c));
            resultSignal.setSucces(true);

        }

        return resultSignal;
        
    }

}
