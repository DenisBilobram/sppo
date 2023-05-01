package lab6.app.input;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import lab6.app.commands.Command;
import lab6.app.commands.CommandAdd;
import lab6.app.commands.CommandClear;
import lab6.app.commands.CommandCountLessAuthor;
import lab6.app.commands.CommandExecute;
import lab6.app.commands.CommandHead;
import lab6.app.commands.CommandHelp;
import lab6.app.commands.CommandInfo;
import lab6.app.commands.CommandMaxByName;
import lab6.app.commands.CommandPrintTunedInWorks;
import lab6.app.commands.CommandRemoveById;
import lab6.app.commands.CommandRemoveHead;
import lab6.app.commands.CommandRemoveLower;
import lab6.app.commands.CommandShow;
import lab6.app.commands.CommandUpdate;
import lab6.app.commands.client.CommandExit;
import lab6.app.labwork.LabWork;
import lab6.app.labwork.Person;

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
        commands.put("show", new CommandShow());
        commands.put("update", new CommandUpdate());
        
    }

    public Command recieveCommand(Scanner scanner, boolean interactive) {

        String[] commandInput = null;

        commandInput = LabWorkInput.getCommand(scanner, interactive);
        if (commandInput == null) {
            return null;
        }

        Command command = commands.get(commandInput[0]);
        
        if (command.isRequireFile()) {
            command.setFile(new File(commandInput[1]));
        } else if (command.isRequireId()) {
            command.setId(commandInput[1]);
        }

        if (command.isRequireLabWork()) {
            LabWork newLabWork = LabWorkInput.getLabWork(scanner, interactive);
            if (newLabWork == null) return null;
            command.setLabWork(newLabWork);
        } else if (command.isRequirePerson()) {
            Person newPerson = LabWorkInput.getPerson(scanner, interactive);
            if (newPerson == null) return null;
            command.setPerson(newPerson);
        }

        return command;
    }
}