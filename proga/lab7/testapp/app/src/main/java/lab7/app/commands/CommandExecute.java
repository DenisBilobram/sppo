package lab7.app.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Scanner;

import lab7.app.signals.Signal;
import lab7.app.input.CommandParser;
import lab7.app.labwork.LabWork;



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

    public Signal execute( PriorityBlockingQueue<LabWork> priorityBlockingQueue) {

        Signal serverSignal = new Signal();

        if (listOfCommands.size() == 0) {
            
            serverSignal.setMessage("В скрипте не найдено валидных команд.");
            serverSignal.setSucces(false);

        } else {

            for (Command command : listOfCommands) {
                serverSignal.setMessage(serverSignal.getMessage() + "\n" + command.execute(priorityBlockingQueue).getMessage());
            }
            serverSignal.setSucces(true);
            serverSignal.setMessage(serverSignal.getMessage() + "\nСкрипт выполнен.");

        }

        return serverSignal;
        
    }

    public Signal pull() {

        Signal resultSignal = new Signal();

        try {

            File file = getFile();

            if (filesList.contains(file)) {
                resultSignal.setMessage("В скрипте найдена рекурсия, завершаю выполнение.");
                filesList.clear();
                return resultSignal;
            }

            filesList.add(file);

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                CommandParser commandParser = new CommandParser();
                Command command = commandParser.recieveCommand(scanner, false, getUser());

                if (command instanceof CommandExecute) {
                    Signal result = ((CommandExecute)command).pull();
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

            resultSignal.setMessage("Скрипт отсканирован.\n" + resultSignal.getMessage());
            resultSignal.setSucces(true);

            filesList.clear();
            return resultSignal;

        } catch (FileNotFoundException exp) {
            resultSignal.setMessage(String.format("Файл %s не найден.", getFile().getPath()));
        }

        filesList.clear();
        return resultSignal;
    }
}
