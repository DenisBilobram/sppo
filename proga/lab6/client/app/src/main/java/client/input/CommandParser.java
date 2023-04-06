package client.input;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import client.commands.Command;
import client.commands.CommandAdd;
import client.commands.CommandClear;
import client.commands.CommandCountLessAuthor;
import client.commands.CommandExecute;
import client.commands.CommandExit;
import client.commands.CommandHead;
import client.commands.CommandHelp;
import client.commands.CommandInfo;
import client.commands.CommandMaxByName;
import client.commands.CommandPrintTunedInWorks;
import client.commands.CommandRemoveById;
import client.commands.CommandRemoveHead;
import client.commands.CommandRemoveLower;
import client.commands.CommandSave;
import client.commands.CommandShow;
import client.commands.CommandUpdate;

/** Класс отвечающий за парсинг строковой комманды в объект.
 * 
 */
public class CommandParser {

    public static HashMap<String, Command> commands = new HashMap<>();
    
    public CommandParser() {

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

    public Command recieveCommand(Scanner scanner) {

        String[] commandInput = null;

        commandInput = LabWorkInput.getCommand(scanner);
        if (commandInput == null) {
            return null;
        }

        Command command = commands.get(commandInput[0]);
        List<Object> operands = List.of(commandInput[1], scanner);
        command.setOperands(operands);
        return command;
    }
}