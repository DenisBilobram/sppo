package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;
import lab7.app.signals.Signal;

/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author_name меньше заданного. 
 * 
 */
public class CommandCountLessAuthor extends Command {

    public CommandCountLessAuthor() {
        this.requirePerson = true;
    }

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        Person author = getPerson();
        Signal resultSignal = new Signal();

        if (author == null) {

            resultSignal.setMessage("Команда не может быть выполнена.");
            resultSignal.setSucces(false);
            return resultSignal;

        } else {

            Long c = priorityBlockingQueue.stream().filter(x -> x.getAuthor().getName().length() < author.getName().length()).count();
            resultSignal.setMessage(String.format("Количество: %d", c));
            resultSignal.setSucces(true);

        }

        return resultSignal;
        
    }

}
