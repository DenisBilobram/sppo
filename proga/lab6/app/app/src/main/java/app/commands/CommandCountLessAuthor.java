package app.commands;

import java.util.PriorityQueue;
import app.labwork.LabWork;
import app.labwork.Person;
import app.signals.Signal;

/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author меньше заданного. 
 * 
 */
public class CommandCountLessAuthor extends Command {

    public CommandCountLessAuthor() {
        this.requirePerson = true;
    }

    @Override
    public Signal execute(PriorityQueue<LabWork> PriorityQueue) {
        Person author = getPerson();
        Signal resultSignal = new Signal();

        if (author == null) {
            resultSignal.setMessage("Команда не может быть выполнена.");
            resultSignal.setSucces(false);
            return resultSignal;
        } else {
            int c = 0;
            for (LabWork labWork : PriorityQueue) {
                if (labWork.getAuthor().getName().length() < author.getName().length()) {
                    c += 1;
                }
            }
            resultSignal.setMessage(String.format("Количество: %d", c));
            resultSignal.setSucces(true);
        }

        return resultSignal;
        
    }

}
