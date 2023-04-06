package client.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import client.input.CommandParser;
import client.labwork.LabWork;


/** Класс команды реализующей выполнение комманд из файла-скрипта. 
 * 
 */
public class CommandExecute extends Command {

    private static List<File> filesList = new ArrayList<>();

    public void execute(PriorityQueue<LabWork> PriorityQueue) {
        try {
            File file = new File((String)operands.get(0));

            if (filesList.contains(file)) {
                System.out.println("В скрипте найдена рекурсия, завершаю выполнение.");
                return;
            }
            filesList.add(file);

            Scanner scanner = new Scanner(file);
            CommandParser receiver = new CommandParser();
            while (scanner.hasNextLine()) {
                receiver.recieveCommand(scanner);
            }
            System.out.println("Скрипт выполнен.");
            scanner.close();
            filesList.remove(file);
        } catch (FileNotFoundException exp) {
            System.out.println("Файл скрипта не найден.");
        }
    }
}
