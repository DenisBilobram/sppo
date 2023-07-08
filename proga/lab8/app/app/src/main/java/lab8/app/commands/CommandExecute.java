package lab8.app.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Scanner;

import lab8.app.signals.ServerSignal;
import lab8.app.signals.Signal;
import lab8.app.input.CommandParser;
import lab8.app.labwork.LabWork;



/** Класс команды реализующей выполнение комманд из файла-скрипта. 
 * 
 */
public class CommandExecute extends Command {

    List<Command> listOfCommands = new ArrayList<>();

    public List<Command> getListOfCommands() {
        return listOfCommands;
    }

    public void setListOfCommands(List<Command> listOfCommands) {
        this.listOfCommands = listOfCommands;
    }

    public CommandExecute() {
        this.requireFile = true;
    }

    private static List<File> filesList = new ArrayList<>();

    public ServerSignal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue, ResourceBundle bundle) {

        ServerSignal serverSignal = new ServerSignal();

        if (listOfCommands.size() == 0) {
            
            serverSignal.setMessage(bundle.getString("novalid"));
            serverSignal.setSucces(false);

        } else {

            for (Command command : listOfCommands) {
                serverSignal.setMessage(serverSignal.getMessage() + "\n" + command.execute(priorityBlockingQueue, bundle).getMessage());
            }
            serverSignal.setSucces(true);
            serverSignal.setMessage(serverSignal.getMessage() + "\n" + bundle.getString("scriptcompl"));

        }

        return serverSignal;
        
    }

    public Signal pull(ResourceBundle bundle) {

        Signal resultSignal = new Signal();

        try {

            File file = getFile();

            if (filesList.contains(file)) {
                resultSignal.setMessage(bundle.getString("recfound"));
                filesList.clear();
                return resultSignal;
            }

            filesList.add(file);

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                CommandParser commandParser = new CommandParser();
                Command command = commandParser.recieveCommand(scanner, false, getUser());

                if (command instanceof CommandExecute) {
                    Signal result = ((CommandExecute)command).pull(bundle);
                    resultSignal.setMessage(result.getMessage());
                    listOfCommands.addAll(((CommandExecute)command).getListOfCommands());
                } else {
                    if (command != null) {
                        listOfCommands.add(command);
                    }   
                }
            }

            scanner.close();
            filesList.remove(file);

            resultSignal.setMessage(bundle.getString("scriptscaned") + "\n" + resultSignal.getMessage());
            resultSignal.setSucces(true);

            filesList.clear();
            return resultSignal;

        } catch (FileNotFoundException exp) {
            resultSignal.setMessage(bundle.getString("file") + " " + getFile().getPath() + " " + bundle.getString("filenotfound"));
        }

        filesList.clear();
        return resultSignal;
    }
}
