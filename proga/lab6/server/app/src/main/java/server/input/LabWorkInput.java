package server.input;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import server.labwork.Color;
import server.labwork.Coordinates;
import server.labwork.Difficulty;
import server.labwork.LabWork;
import server.labwork.Person;
import server.labwork.fields.CoordinatesField;
import server.labwork.fields.CreationDateField;
import server.labwork.fields.DifficultyField;
import server.labwork.fields.MinimalPointField;
import server.labwork.fields.NameField;
import server.labwork.fields.TunedInWorksField;
import server.labwork.fields.author.EyeColorField;
import server.labwork.fields.author.HeightField;
import server.network.Receiver;

public class LabWorkInput {

    public static String[] getCommand(Scanner scanner) {
        while (true) {
            String line = null;
            System.out.print("\n> ");
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException exp) {
                return null;
            }
            String[] parsedLine = line.split(" ");
            if (Receiver.commands.keySet().contains(parsedLine[0])) {
                if (parsedLine.length == 1) {
                    String[] returnValue = {parsedLine[0], ""};
                    return returnValue;
                }
                return parsedLine;
            }
            System.out.print("\nНеверный ввод команды, попробуйте ещё раз. ");
        }
    }


    public static LabWork getLabWork(Scanner scanner) {
        
    }



    public static Person getPerson(Scanner scanner) {
        
    }


    
    public static boolean getYesNo(Scanner scanner) {
        
    }
}
