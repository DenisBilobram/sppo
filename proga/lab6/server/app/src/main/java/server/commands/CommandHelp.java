package server.commands;

import java.util.PriorityQueue;

import server.labwork.LabWork;

/** Класс команды реализующей отображение всех доступных комманд.
 * 
 */
public class CommandHelp implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        System.out.println("Команды: add {element}, clear, execute_script {file name}, exite, count_less_than_author {author}, " + 
                           "info, head, remove_by_id {id},\nmax_by_name, save, show, remove_head, remove_lower, " +
                           "print_field_descending_tuned_in_works, update {id} {element}");
    }
    
}
