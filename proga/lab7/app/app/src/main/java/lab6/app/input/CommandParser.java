package lab7.app.input;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import lab7.app.commands.Command;
import lab7.app.commands.CommandAdd;
import lab7.app.commands.CommandClear;
import lab7.app.commands.CommandCountLessAuthor;
import lab7.app.commands.CommandExecute;
import lab7.app.commands.CommandHead;
import lab7.app.commands.CommandHelp;
import lab7.app.commands.CommandInfo;
import lab7.app.commands.CommandMaxByName;
import lab7.app.commands.CommandPrintTunedInWorks;
import lab7.app.commands.CommandRemoveById;
import lab7.app.commands.CommandRemoveHead;
import lab7.app.commands.CommandRemoveLower;
import lab7.app.commands.CommandShow;
import lab7.app.commands.CommandUpdate;
import lab7.app.commands.client.CommandExit;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;

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