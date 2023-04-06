package app.client.input;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import app.labwork.Color;
import app.labwork.Coordinates;
import app.labwork.Difficulty;
import app.labwork.LabWork;
import app.labwork.Person;
import app.labwork.fields.CoordinatesField;
import app.labwork.fields.CreationDateField;
import app.labwork.fields.DifficultyField;
import app.labwork.fields.MinimalPointField;
import app.labwork.fields.NameField;
import app.labwork.fields.TunedInWorksField;
import app.labwork.fields.author.EyeColorField;
import app.labwork.fields.author.HeightField;

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
            if (CommandParser.commands.keySet().contains(parsedLine[0])) {
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
        System.out.printf("\nИспользовать дефолтные значения для полей?(yes/no): ");
        if (getYesNo(scanner)) {
            System.out.println("Использую дефолтные значения.");
            return new LabWork("proga", new Coordinates(0, 0), new Date(), 10l, 45l, Difficulty.NORMAL, new Person("denis", 185, Color.BLACK));
        }

        String[] fieldNames = {"name", "coordinates(x y)", "minimalPoint", "tunedInWorks",
                           "difficulty(NORMAL, HARD, INSANE, TERRIBLE)"};

        int counter = 0;
        NameField nameField = new NameField();
        CoordinatesField coordinatesField = new CoordinatesField();
        CreationDateField creationDateField = new CreationDateField();
        creationDateField.putValue(new Date());
        MinimalPointField minimalPointField = new MinimalPointField();
        TunedInWorksField tunedInWorksField = new TunedInWorksField();
        DifficultyField difficultyField = new DifficultyField();

        int misstakeCounter = 0;
        while (counter < 5) {
            try {
                if (misstakeCounter == 0) {
                    System.out.printf("\nВведите значение для поля %s: ", fieldNames[counter]);
                } else if (misstakeCounter < 3){
                    System.out.printf("\nНеподходящее значение для поля %s, попробуйте ещё раз: ", fieldNames[counter]);
                } else {
                    System.out.printf("\nСлишком много попыток ввести значение. Завершение ввода...");
                    return null;
                }
                String line = scanner.nextLine();

                if (line == "") {
                    line = null;
                }
                
                int oldCounter = counter;

                if (counter == 0) {
                    nameField.putValue(nameField.toType(line));
                    if (nameField.validate()) {
                        counter += 1;
                    }
                } else if (counter == 1) {
                    coordinatesField.putValue(coordinatesField.toType(line));
                    if (coordinatesField.validate()) {
                        counter += 1;
                    }
                } else if (counter == 2) {
                    minimalPointField.putValue(minimalPointField.toType(line));
                    if (minimalPointField.validate()) {
                        counter += 1;
                    }
                } else if (counter == 3) {
                    tunedInWorksField.putValue(tunedInWorksField.toType(line));
                    if (tunedInWorksField.validate()) {
                        counter += 1;
                    }
                } else if (counter == 4) {
                    difficultyField.putValue(difficultyField.toType(line));
                    if (difficultyField.validate()) {
                        counter += 1;
                    }
                }

                if (oldCounter == counter) {
                    misstakeCounter += 1;
                } else {
                    misstakeCounter = 0;
                }


            } catch (NoSuchElementException exp) {
                return null;
            } catch (Exception e) {
                misstakeCounter += 1;
            }
        }

        String name = nameField.getValue();
        Coordinates coordinates = coordinatesField.getValue();
        Date creationDate = creationDateField.getValue();
        Long minimalPoint = minimalPointField.getValue();
        Long tunedInWorks = tunedInWorksField.getValue();
        Difficulty difficulty = difficultyField.getValue();

        Person author = getPerson(scanner);
        if (author == null) {
            return null;
        }
        
        return new LabWork(name, coordinates, creationDate, minimalPoint, tunedInWorks, difficulty, author);
    }



    public static Person getPerson(Scanner scanner) {
        String[] fields = {"author name", "author height", "author eye color(RED, BLACK, YELLOW, BROWN)"};
        NameField nameField = new NameField();
        HeightField heightField = new HeightField();
        EyeColorField eyeColorField = new EyeColorField();
        
        int counter = 0;
        int misstakeCounter = 0;

        while (counter < 3) {
            try {
                if (misstakeCounter == 0) {
                    System.out.printf("\nВведите значение для поля %s: ", fields[counter]);
                } else if (misstakeCounter < 3){
                    System.out.printf("\nНеподходящее значение для поля %s, попробуйте ещё раз: ", fields[counter]);
                } else {
                    System.out.printf("\nСлишком много попыток ввести значение. Завершение ввода...");
                    return null;
                }
                String line = scanner.nextLine();

                if (line == "") {
                    line = null;
                }

                int oldCounter = counter;

                if (counter == 0) {
                   nameField.putValue(nameField.toType(line));
                   if (nameField.validate()) {
                    counter += 1;
                   }
                } else if (counter == 1) {
                    heightField.putValue(heightField.toType(line));
                   if (heightField.validate()) {
                    counter += 1;
                   }
                } else if (counter == 2) {
                    eyeColorField.putValue(eyeColorField.toType(line));
                    if (eyeColorField.validate()) {
                     counter += 1;
                    }
                }

                if (oldCounter == counter) {
                    misstakeCounter += 1;
                } else {
                    misstakeCounter = 0;
                }
                
            } catch (NoSuchElementException exp) {
                return null;
            } catch (Exception e) {
                misstakeCounter += 1;
            } 
        }
        return new Person(nameField.getValue(), heightField.getValue(), eyeColorField.getValue());
    }


    
    public static boolean getYesNo(Scanner scanner) {
        int misstakeCounter = 0;
        while (true) {
            try {
                if (misstakeCounter != 0 && misstakeCounter < 3) {
                    System.out.printf("\nНеверное значение, попробуйте ещё раз(yes/no):");
                } else if (misstakeCounter >= 3) {
                    System.out.printf("\nСлишком много попыток, использую дефолтные значения.");
                    return true;
                }
                String line = scanner.nextLine();
                if (line.equals("yes")) {
                    return true;
                } else if (line.equals("no")) {
                    return false;
                }
                misstakeCounter += 1;
            } catch (NoSuchElementException exp) {
                System.out.println("\nКонец скрипта.");
                return true;
            }
        }
    }
}
