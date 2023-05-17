package lab6.app.commands;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import lab6.app.labwork.LabWork;
import lab6.app.signals.Signal;

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

            LabWork labWorkUpdate = getLabWork();
            LabWork labWorkToChame = priorityQueue.stream().filter(x -> x.getId().equals(Long.parseLong(getId()))).findFirst().get();

            labWorkToChame.setName(labWorkUpdate.getName());
            labWorkToChame.setCoordinates(labWorkUpdate.getCoordinates());
            labWorkToChame.setDifficulty(labWorkUpdate.getDifficulty());
            labWorkToChame.setMinimalPoint(labWorkUpdate.getMinimalPoint());
            labWorkToChame.setTunedInWorks(labWorkUpdate.getTunedInWorks());
            labWorkToChame.setAuthor(labWorkUpdate.getAuthor());
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