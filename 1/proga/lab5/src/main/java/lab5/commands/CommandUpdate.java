package lab5.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.labwork.LabWork;
import lab5.labwork.LabWorkInput;

/** Класс команды реализующей изменение элемента по его id.
 * 
 */
public class CommandUpdate implements Command {

    @Override
    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand) {
    }

    public void execute(PriorityQueue<LabWork> priorityQueue, Object operand, Scanner scanner) {
        
        Iterator<LabWork> iter = priorityQueue.iterator();
        while(iter.hasNext()) {
            LabWork labWork = iter.next();
            try {
                if (labWork.getId().equals(Long.parseLong((String)operand))) {
                    LabWork labWorkNew = LabWorkInput.getLabWork(scanner);
                    if (labWorkNew == null) {
                        System.out.println("Элемент не был изменён.");
                        return;
                    }
                    labWork.setName(labWorkNew.getName());
                    labWork.setCoordinates(labWorkNew.getCoordinates());
                    labWork.setDifficulty(labWorkNew.getDifficulty());
                    labWork.setMinimalPoint(labWorkNew.getMinimalPoint());
                    labWork.setTunedInWorks(labWorkNew.getTunedInWorks());
                    labWork.setAuthor(labWorkNew.getAuthor());
                    System.out.println("Элемент был изменём.");
                    return;
                } 
            } catch (NumberFormatException exp) {
                System.out.println("Неверный формат аргумента id.");
                return;
            }
        }
        System.out.println("Элемент с таким id не найден.");
    }
    
}