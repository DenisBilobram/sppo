package app.client.input;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import app.commands.Command;
import app.commands.CommandAdd;
import app.commands.CommandClear;
import app.commands.CommandCountLessAuthor;
import app.commands.CommandHead;
import app.commands.CommandHelp;
import app.commands.CommandInfo;
import app.commands.CommandMaxByName;
import app.commands.CommandPrintTunedInWorks;
import app.commands.CommandRemoveById;
import app.commands.CommandRemoveHead;
import app.commands.CommandRemoveLower;
import app.commands.CommandShow;
import app.commands.CommandUpdate;
import app.commands.client.CommandExecute;
import app.commands.client.CommandExit;
import app.labwork.LabWork;
import app.labwork.Person;

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

    public Command recieveCommand(Scanner scanner) {

        String[] commandInput = null;

        commandInput = LabWorkInput.getCommand(scanner);
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
            LabWork newLabWork = LabWorkInput.getLabWork(scanner);
            if (newLabWork == null) return null;
            command.setLabWork(newLabWork);
        } else if (command.isRequirePerson()) {
            Person newPerson = LabWorkInput.getPerson(scanner);
            if (newPerson == null) return null;
            command.setPerson(newPerson);
        }

        return command;
    }
}