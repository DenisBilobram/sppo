package lab8.app.commands;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

public class CommandRemoveLower extends Command {

    

    public CommandRemoveLower() {
        this.requireLabWork = true;
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal resultSignal = new ServerSignal();

        LabWork userLabWork = labWorkNew;

        List<LabWork> labWorksOwner = priorityBlockingQueue.stream().filter(labWork -> labWork.getOwner().getId() == getUser().getId()).toList();

        List<LabWork> labWorksToDelete = labWorksOwner.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).toList();

        if (labWorksToDelete.size() == 0) {
            resultSignal.setMessage(bundle.getString("errtuned"));
            resultSignal.setSucces(false);
            return resultSignal;
        }

        for (LabWork labWork : labWorksToDelete) {

            boolean deleted = DataBase.deleteLabWorkById(labWork.getId());
            if (!deleted) {
                resultSignal.setMessage(bundle.getString("elnotdel"));
                resultSignal.setSucces(false);
                return resultSignal;
            }
        }
        
        priorityBlockingQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).forEach(x -> priorityBlockingQueue.remove(x));

        resultSignal.setMessage(bundle.getString("tunedsuc"));
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
