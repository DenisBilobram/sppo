package server.network;

import java.net.Socket;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import server.commands.Command;
import server.commands.CommandAdd;
import server.commands.CommandClear;
import server.commands.CommandCountLessAuthor;
import server.commands.CommandExecute;
import server.commands.CommandExit;
import server.commands.CommandHead;
import server.commands.CommandHelp;
import server.commands.CommandInfo;
import server.commands.CommandMaxByName;
import server.commands.CommandPrintTunedInWorks;
import server.commands.CommandRemoveById;
import server.commands.CommandRemoveHead;
import server.commands.CommandRemoveLower;
import server.commands.CommandSave;
import server.commands.CommandShow;
import server.commands.CommandUpdate;
import server.input.LabWorkInput;
import server.labwork.LabWork;

/** Класс отвечающий за парсинг строковой комманды в объект.
 * 
 */
public class Receiver {

    public static HashMap<String, Command> commands = new HashMap<>();
    private PriorityQueue<LabWork> priorityQueue;
    public static Long maxId = 0l;
    

    public Receiver(PriorityQueue<LabWork> priorityQueue) {
        this.priorityQueue = priorityQueue;
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