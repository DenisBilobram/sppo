package lab8.app.commands;

import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

public class CommandLook extends Command {

    public CommandLook() {
        this.requireId = true;
        this.description = "";
    }

    @Override
    public ServerSignal execute(PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal serverSignal = new ServerSignal();

        LabWork labWork = priorityBlockingQueue.stream().filter(labWorkFind -> labWorkFind.getId() == Long.parseLong(this.getId())).findFirst().get();
        if (labWork != null) {
            serverSignal.setMessage(labWork.toString());
            serverSignal.setSucces(true);
        } else {
            serverSignal.setMessage(bundle.getString("elnotfound"));
            serverSignal.setSucces(false);
        }
        
        return serverSignal;

    }
    
}
