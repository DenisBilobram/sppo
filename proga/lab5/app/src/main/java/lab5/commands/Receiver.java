package lab5.commands;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.labwork.LabWork;
import lab5.labwork.LabWorkInput;

/** Класс отвечающий за парсинг строковой комманды в объект.
 * 
 */
public class Receiver {

    public static HashMap<String, Command> commands = new HashMap<>();
    private PriorityQueue<LabWork> priorityQueue;
    public static Long maxId = 0l;
    

    public Receiver(PriorityQueue<LabWork> priorityQueue) {
        this.priorityQueue = priorityQueue;
        commands.put("add", new CommandAdd());
        commands.put("info", new CommandInfo());
        commands.put("clear", new CommandClear());
        commands.put("count_less_than_author", new CommandCountLessAuthor());
        commands.put("execute_script", new CommandExecute());
        commands.put("exit", new CommandExit());
        commands.put("head", new CommandHead());
        commands.put("help", new CommandHelp());
        commands.put("max_by_name", new CommandMaxByName());
        commands.put("print_field_descending_tuned_in_works", new CommandPrintTunedInWorks());
        commands.put("remove_by_id", new CommandRemoveById());
        commands.put("remove_head", new CommandRemoveHead());
        commands.put("remove_lower", new CommandRemoveLower());
        commands.put("save", new CommandSave());
        commands.put("show", new CommandShow());
        commands.put("update", new CommandUpdate());
    }

    public void recieveCommand(Scanner scanner) {
        String[] commandInput = null;

        commandInput = LabWorkInput.getCommand(scanner);
        if (commandInput == null) {
            return;
        }

        Command command = commands.get(commandInput[0]);
        if (command instanceof CommandUpdate) {
            ((CommandUpdate)command).execute(priorityQueue, commandInput[1], scanner);
        }
        if (commandInput[1] == "") {
            command.execute(this.priorityQueue, scanner);
        } else {
            command.execute(this.priorityQueue, commandInput[1]);
        }
    }
}