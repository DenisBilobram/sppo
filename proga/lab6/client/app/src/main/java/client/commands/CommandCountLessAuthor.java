package client.commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import client.input.LabWorkInput;
import client.labwork.LabWork;
import client.labwork.Person;


/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author меньше заданного. 
 * 
 */
public class CommandCountLessAuthor extends Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue) {
        Scanner scanner = (Scanner)operands.get(0);
        Person author = LabWorkInput.getPerson(scanner);
        if (author == null) {
            System.out.println("Команда не может быть выполнена.");
            return;
        }
        int c = 0;
        for (LabWork labWork : PriorityQueue) {
            if (labWork.getAuthor().getName().length() < author.getName().length()) {
                c += 1;
            }
        }
        System.out.println(String.format("Количество: %d", c));
    }

}
