package lab8.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;


/** Класс команды реализующей удаление всех элементов из коллекции.
 * 
 */
public class CommandClear extends Command{
    
    public CommandClear() {
        this.description = "Комманда Clear полностью очищает коллекцию элементов LabWork.";
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        ServerSignal resultSignal = new ServerSignal();

        boolean cleared = DataBase.deleteAllLabWorks();
        if (cleared) {
            priorityBlockingQueue.clear();
            resultSignal.setMessage("Коллекция была очищена.");
            resultSignal.setSucces(true);
        } else {
            resultSignal.setMessage("Ошибка при очистке коллекции.");
            resultSignal.setSucces(false);
        }

        return resultSignal;
    }

}
