package lab6.app.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import lab6.app.signals.Signal;
import lab6.app.input.CommandParser;
import lab6.app.labwork.LabWork;



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

    public Signal execute(PriorityQueue<LabWork> priorityQueue) {

        Signal resultSignal = new Signal();
        return resultSignal;
        
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
                Command command = commandParser.recieveCommand(scanner, false);
                if (command == null) {
                    continue;
                }

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
