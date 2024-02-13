package lab8.app.commands;

import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

/** Класс команды реализующей изменение элемента по его id.
 * 
 */
public class CommandUpdate extends Command {

    public CommandUpdate() {
        this.requireLabWork = true;
        this.requireId = true;
    }

    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();

        try {
            
            LabWork labWorkUpdate = getLabWorkNew();

            LabWork labWork = priorityBlockingQueue.stream().filter(x -> x.getId().equals(Long.parseLong(getId()))).findFirst().get();

            if (labWork.getOwner().getId() != getUser().getId()) {
                resultSignal.setMessage(bundle.getString("notowner"));
                resultSignal.setSucces(false);
                return resultSignal;
            }

            boolean updated = DataBase.updateLabWorkById(labWork.getId(), labWorkUpdate);

            if (!updated) {
                resultSignal.setMessage(bundle.getString("notupd"));
                resultSignal.setSucces(false);
                return resultSignal;
            }

            labWork.setName(labWorkUpdate.getName());
            labWork.setCoordinates(labWorkUpdate.getCoordinates());
            labWork.setDifficulty(labWorkUpdate.getDifficulty());
            labWork.setMinimalPoint(labWorkUpdate.getMinimalPoint());
            labWork.setTunedInWorks(labWorkUpdate.getTunedInWorks());
            labWork.setAuthor(labWorkUpdate.getAuthor());
            resultSignal.setMessage(bundle.getString("updsuc"));
            resultSignal.setSucces(true);

        } catch (NumberFormatException exp) {
            resultSignal.setMessage(bundle.getString("idformer"));
            resultSignal.setSucces(false);
        } catch (NoSuchElementException exp) {
            resultSignal.setMessage(bundle.getString("elnotfound"));
            resultSignal.setSucces(false);
        }
        
        return resultSignal;
    }
    
}