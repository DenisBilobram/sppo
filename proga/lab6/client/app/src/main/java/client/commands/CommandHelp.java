package client.commands;

import java.util.PriorityQueue;

import client.labwork.LabWork;

/** Класс команды реализующей отображение всех доступных комманд.
 * 
 */
public class CommandHelp extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue) {
        System.out.println("Команды:\nadd {element}\nclear\nexecute_script {file name}\nexite\ncount_less_than_author {author}\n" + 
                           "info\nhead\nremove_by_id {id}\nmax_by_name\nsave\nshow\nremove_head\nremove_lower\n" +
                           "print_field_descending_tuned_in_works\nupdate {id} {element}");
    }
    
}
