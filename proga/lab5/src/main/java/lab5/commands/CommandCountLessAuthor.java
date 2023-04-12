package lab5.commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.labwork.LabWork;
import lab5.labwork.LabWorkInput;
import lab5.labwork.Person;


/** Класс команды реализующей подсчет кол-ва элементов коллекции, у которых поле author меньше заданного. 
 * 
 */
public class CommandCountLessAuthor implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> PriorityQueue, Object operand) {
        Scanner scanner = (Scanner)operand;
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
