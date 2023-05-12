package lab7.app.commands;

import java.util.PriorityQueue;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;


/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{

    @Override
    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();

        boolean cleared = DataBase.deleteAllLabWorks();
        if (cleared) {
            priorityQueue.clear();
            resultSignal.setMessage("Коллекция была очищена.");
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage("Ошибка при очистке коллекции.");
            resultSignal.setSucces(false);
        }

        return resultSignal;
    }

}
