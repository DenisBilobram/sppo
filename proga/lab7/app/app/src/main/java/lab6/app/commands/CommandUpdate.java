package lab7.app.commands;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
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

    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();

        try {
            
            LabWork labWorkUpdate = priorityQueue.stream().filter(x -> x.getId().equals(Long.parseLong(getId()))).findFirst().get();

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