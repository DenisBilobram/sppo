package app.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import app.labwork.LabWork;
import app.signals.Signal;

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
        
        Iterator<LabWork> iter = priorityQueue.iterator();
        while(iter.hasNext()) {
            LabWork labWork = iter.next();
            try {
                if (labWork.getId().equals(Long.parseLong(id))) {
                    LabWork labWorkNew = labWork;
                    labWork.setName(labWorkNew.getName());
                    labWork.setCoordinates(labWorkNew.getCoordinates());
                    labWork.setDifficulty(labWorkNew.getDifficulty());
                    labWork.setMinimalPoint(labWorkNew.getMinimalPoint());
                    labWork.setTunedInWorks(labWorkNew.getTunedInWorks());
                    labWork.setAuthor(labWorkNew.getAuthor());
                    resultSignal.setMessage("Элемент был изменём.");
                    resultSignal.setSucces(true);
                    return resultSignal;
                } 
            } catch (NumberFormatException exp) {
                System.out.println();
                resultSignal.setMessage("Неверный формат аргумента id.");
                resultSignal.setSucces(false);
                return resultSignal;
            }
            
        }
        resultSignal.setMessage("Элемент с таким id не найден.");
        resultSignal.setSucces(false);
        return resultSignal;
    }
    
}