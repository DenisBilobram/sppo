package lab5;

import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.commands.Receiver;
import lab5.database.DataReader;
import lab5.database.Database;
import lab5.labwork.LabWork;

public class App {

    public static void main(String[] args) {

        Database dataBase = new Database();
        DataReader dataReader = new DataReader(dataBase);

        PriorityQueue<LabWork> priorityQueue = dataReader.read();
        Long maxId = 0l;

        for (LabWork lab : priorityQueue) {
            if (lab.getId() > maxId) {
                maxId = lab.getId();
            }
        }

        Receiver rc = new Receiver(priorityQueue);
        Receiver.maxId = maxId;
        System.out.print("Для просмотра доступных команд введите help.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            rc.recieveCommand(scanner);
        }
    }
}
