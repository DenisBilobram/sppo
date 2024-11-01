package lab7.app.commands;

import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.database.DataBase;
import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей изменение элемента по его id.
 * 
 */
public class CommandUpdate extends Command {

    public CommandUpdate() {
        this.requireLabWork = true;
        this.requireId = true;
    }

    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        Signal resultSignal = new Signal();

        try {
            
            LabWork labWorkUpdate = getLabWorkUpdate();

            LabWork labWork = priorityBlockingQueue.stream().filter(x -> x.getId().equals(Long.parseLong(getId()))).findFirst().get();

            if (labWork.getAuthor().getId() != DataBase.readProfileByUserId(getUser().getId()).getId()) {
                resultSignal.setMessage("Вы не являетесь владельцем данного LabWork.");
                resultSignal.setSucces(false);
                return resultSignal;
            }

            boolean updated = DataBase.updateLabWorkById(labWork.getId(), labWorkUpdate);

            if (!updated) {
                resultSignal.setMessage("Не удалось изменить объект по техническим причинам.");
                resultSignal.setSucces(false);
                return resultSignal;
            }

            labWork.setName(labWorkUpdate.getName());
            labWork.setCoordinates(labWorkUpdate.getCoordinates());
            labWork.setDifficulty(labWorkUpdate.getDifficulty());
            labWork.setMinimalPoint(labWorkUpdate.getMinimalPoint());
            labWork.setTunedInWorks(labWorkUpdate.getTunedInWorks());
            labWork.setAuthor(labWorkUpdate.getAuthor());
            resultSignal.setMessage("Элемент был изменём.");
            resultSignal.setSucces(true);

        } catch (NumberFormatException exp) {
            resultSignal.setMessage("Неверный формат аргумента id.");
            resultSignal.setSucces(false);
        } catch (NoSuchElementException exp) {
            resultSignal.setMessage("Элемент с таким id не найден.");
            resultSignal.setSucces(false);
        }
        
        return resultSignal;
    }
    
}