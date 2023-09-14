package lab7.app.input;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import lab7.app.auth.User;
import lab7.app.auth.commands.AuthCommand;
import lab7.app.auth.commands.LoginCommand;
import lab7.app.auth.commands.RegistrationCommand;
import lab7.app.labwork.Coordinates;
import lab7.app.labwork.Difficulty;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;
import lab7.app.labwork.fields.CoordinatesField;
import lab7.app.labwork.fields.CreationDateField;
import lab7.app.labwork.fields.DifficultyField;
import lab7.app.labwork.fields.MinimalPointField;
import lab7.app.labwork.fields.NameField;
import lab7.app.labwork.fields.TunedInWorksField;
import lab7.app.labwork.fields.person.EyeColorField;
import lab7.app.labwork.fields.person.HeightField;
import lab7.app.labwork.fields.user.PasswordField;
import lab7.app.labwork.fields.user.UsernameField;
import lab7.app.signals.SignalManager;

public class LabWorkInput {

    public static String[] getCommand(Scanner scanner, boolean interactiv, User user) {
        
        while (true) {
            
            String line = null;
            String username = user != null ? user.getUsername() : "";

            if (interactiv) {
                System.out.print(String.format("%s> ", username));
            }
            
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
            if (interactiv) {
                SignalManager.printMessage("Неверный ввод команды, попробуйте ещё раз.", true);
            }
        }
        
    }


    public static LabWork getLabWork(Scanner scanner, boolean interactiv) {
        if (interactiv) {
            SignalManager.printMessage("Использовать дефолтные значения для полей?(yes/no): ", false);
        }
        if (getYesNo(scanner, interactiv)) {
            return new LabWork("proga", new Coordinates(0, 0), new Date(), 10l, 45l, Difficulty.NORMAL, null);
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
                    if (interactiv) {
                        SignalManager.printMessage(String.format("Введите значение для поля %s: ", fieldNames[counter]), false);
                    }
                } else if (misstakeCounter < 3){
                    if (interactiv) {
                        SignalManager.printMessage(String.format("Неподходящее значение для поля %s, попробуйте ещё раз: ", fieldNames[counter]), false);
                    }
                } else {
                    if (interactiv) {
                        SignalManager.printMessage("\nСлишком много попыток ввести значение. Завершение ввода...", true);
                    }
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
        
        return new LabWork(name, coordinates, creationDate, minimalPoint, tunedInWorks, difficulty, null);
    }



    public static Person getPerson(Scanner scanner, boolean interactiv) {
        
        String[] fields = {"author name", "author height", "author eye color(RED, BLACK, YELLOW, BROWN)"};
        NameField nameField = new NameField();
        HeightField heightField = new HeightField();
        EyeColorField eyeColorField = new EyeColorField();
        
        int counter = 0;
        int misstakeCounter = 0;

        while (counter < 3) {
            try {
                if (misstakeCounter == 0) {
                    if (interactiv) {
                        SignalManager.printMessage(String.format("Введите значение для поля %s: ", fields[counter]), false);
                    }
                } else if (misstakeCounter < 3){
                    if (interactiv) {
                        SignalManager.printMessage(String.format("Неподходящее значение для поля %s, попробуйте ещё раз: ", fields[counter]), false);
                    }
                } else {
                    if (interactiv) {
                        SignalManager.printMessage("Слишком много попыток ввести значение. Завершение ввода...", true);
                    }
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
                    if (!interactiv) {
                        misstakeCounter = 3;
                    }
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


    
    public static boolean getYesNo(Scanner scanner, boolean interactiv) {
        
        int misstakeCounter = 0;
        while (true) {
            try {
                if (misstakeCounter != 0 && misstakeCounter < 3) {
                    if (interactiv) {
                        SignalManager.printMessage("Неверное значение, попробуйте ещё раз(yes/no): ", false);
                    }
                } else if (misstakeCounter >= 3) {
                    if (interactiv) {
                        SignalManager.printMessage("Слишком много попыток, использую дефолтные значения.", true);
                    }
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
                return true;
            }
        }
    }

    public static AuthCommand getAuthOption(Scanner scanner) {

        while (true) {
            System.out.println("Для авторизации введите login, для регистрации введите register.");
            System.out.print("> ");
            String line = scanner.nextLine();

            if (line.equals("register")) {
                return new RegistrationCommand();
            } else if (line.equals("login")) {
                return new LoginCommand();
            } else if (line.equals("exit")) {
                System.exit(0);
            }

        }
        
        
    }

    public static User getRegister(Scanner scanner) {

        User user = getLogin(scanner);
        if (user == null) {
            return null;
        }
        Person person = getPerson(scanner, true);
        if (person == null) {
            return null;
        }

        user.setProfile(person);

        return user;
    }

    public static User getLogin(Scanner scanner) {

        UsernameField usernameField = new UsernameField();
        PasswordField passwordField = new PasswordField();

        int misstakeCounter = 0;

        while (true) {
            System.out.println("Введите username: ");
            String line = scanner.nextLine();
            usernameField.putValue(line);
            if (usernameField.validate()) {
                misstakeCounter = 0;
                break;
            } else {
                misstakeCounter += 1;
                if (misstakeCounter == 3) {
                    return null;
                }
            }
        }

        while (true) {
            System.out.println("Введите password: ");
            String line = scanner.nextLine();
            passwordField.putValue(line);
            if (passwordField.validate()) {
                misstakeCounter = 0;
                break;
            } else {
                misstakeCounter += 1;
                if (misstakeCounter == 3) {
                    return null;
                }
            }
        }
        return new User(usernameField.getValue(), passwordField.getValue());
    }
}
