package client.commands;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import client.input.LabWorkInput;
import client.labwork.LabWork;

/** Класс команды реализующей изменение элемента по его id.
 * 
 */
public class CommandUpdate extends Command {

    public void execute(PriorityQueue<LabWork> priorityQueue) {
        
        Iterator<LabWork> iter = priorityQueue.iterator();
        while(iter.hasNext()) {
            LabWork labWork = iter.next();
            Scanner scanner = (Scanner)operands.get(1);
            try {
                if (labWork.getId().equals(Long.parseLong((String)operands.get(0)))) {
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