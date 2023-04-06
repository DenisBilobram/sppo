package server.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import server.labwork.LabWork;
import server.network.Receiver;


/** Класс команды реализующей выполнение комманд из файла-скрипта. 
 * 
 */
public class CommandExecute implements Command {

    private static List<File> filesList = new ArrayList<>();

    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        try {
            File file = new File((String)operand);

            if (filesList.contains(file)) {
                System.out.println("В скрипте найдена рекурсия, завершаю выполнение.");
                return;
            }
            filesList.add(file);

            Scanner scanner = new Scanner(file);
            Receiver receiver = new Receiver(PriorityQueue);
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
