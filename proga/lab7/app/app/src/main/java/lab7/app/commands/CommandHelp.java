package lab7.app.commands;

import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.labwork.LabWork;
import lab7.app.signals.Signal;

/** Класс команды реализующей отображение всех доступных комманд.
 * 
 */
public class CommandHelp extends Command {

    @Override
    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {
        Signal resultSignal = new Signal("Команды:\nadd {element}\nclear\nexecute_script {file name}\nexite\ncount_less_than_author {author}\n" + 
        "info\nhead\nremove_by_id {id}\nmax_by_name\nshow\nremove_head\nremove_lower\n" +
        "print_field_descending_tuned_in_works\nupdate {id} {element}");
        resultSignal.setSucces(true);
        return resultSignal;
    }
    
}
