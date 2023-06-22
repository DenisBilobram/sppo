package lab8.app.commands;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import lab8.app.database.DataBase;
import lab8.app.labwork.LabWork;
import lab8.app.signals.ServerSignal;

public class CommandRemoveLower extends Command {

    

    public CommandRemoveLower() {
        this.description = "Команда Remove lower удаляет все элементы с полем Tuned in works меньше заданного.";
        this.requireLabWork = true;
    }

    @Override
    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        LabWork userLabWork = labWorkNew;

        List<LabWork> labWorks = priorityBlockingQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).toList();

        for (LabWork labWork : labWorks) {

            boolean deleted = DataBase.deleteLabWorkById(labWork.getId());
            if (!deleted) {
                ServerSignal resultSignal = new ServerSignal("Не удалось удалить элементы по техническим причинам.");
                resultSignal.setSucces(false);
                return resultSignal;
            }
        }
        
        priorityBlockingQueue.stream().filter(x -> x.getTunedInWorks() < userLabWork.getTunedInWorks()).forEach(x -> priorityBlockingQueue.remove(x));

        ServerSignal resultSignal = new ServerSignal("Элементы с полем tunedInWorks меньше заданного, если таковые были, удалены.");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
