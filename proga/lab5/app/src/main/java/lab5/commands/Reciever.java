package lab5.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import lab5.Color;
import lab5.Coordinates;
import lab5.Difficulty;
import lab5.LabWork;
import lab5.Person;
import lab5.database.Database;

public class Reciever {

    public static Long max_id;

    private File file;

    private Database database;

    public Reciever(Long id, Database db) {
        Reciever.max_id = id;
        this.database = db;
    }

    public Reciever(Long id, File f, Database db) {
        Reciever.max_id = id;
        this.file = f;
        this.database = db;
    }

    public Command commandRecive(Scanner scanner) {
        if (scanner == null) {
            if (this.file == null) {
                scanner = new Scanner(System.in);
            } else {
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    System.out.println("Файл не найден.");
                    scanner = new Scanner(System.in);
                }
            }
        }
        while (true) {
            if (this.file == null) {
                System.out.println("Введите команду: ");
            }
            String input;
            if (this.file == null) {
                input = scanner.nextLine();
            } else {
                if (scanner.hasNext()) {
                    input = scanner.nextLine();
                } else {
                    return new RepeatCommand();
                }
            }
            try {
                String[] words = {"name: ", "coordinates(x): ", "coordinates(y): ",
                    "minimal point: ", "tuned in works: ", "difficulty(NORMAL, HARD, INSANE, TERRIBLE): ",
                    "author name: ", "author weight: ", "author eye color(BLACK, ORANGE, WHITE, BROWN): ", "author hair color(BLACK, ORANGE, WHITE, BROWN): "};
                String[] arr = input.split(" ");

                if (arr[0].equals("help")) {
                    return new CommandHelp();
                }

                if (arr[0].equals("add")) {
                    List<String> attrs = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (this.file == null) {
                            System.out.println(words[i]);
                        } 
                        attrs.add(scanner.nextLine());
                    }
                    attrs.add(0, null);
                    attrs.add(4, null);
                    LabWork lab = labWorkConstruct(attrs, true);
                    if (lab != null) {
                        max_id += 1;
                        return new CommandAdd(lab);
                    }
                }

                if (arr[0].equals("update")) {
                    List<String> attrs = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (this.file == null) {
                            System.out.println(words[i]);
                        } 
                        attrs.add(scanner.nextLine());
                    }
                    attrs.add(0, null);
                    attrs.add(4, null);
                    LabWork lab = labWorkConstruct(attrs, true);
                    if (lab != null) {
                        Long id = Long.parseLong(arr[1]);
                        return new CommandUpdate(lab, id);
                    }
                }

                if (arr[0].equals("count") && arr[1].equals("less") && arr[2].equals("than")) {
                    List<String> attrs = new ArrayList<>();
                    String authorWords[] = {"Author name: ", "Author weight: ", "Eye color: ", "Hair color: "};
                    for (int i = 0; i < 4; i++) {
                        if (this.file == null) {
                            System.out.println(authorWords[i]);
                        } 
                        attrs.add(scanner.nextLine());
                    }
                    String name = attrs.get(0);
                    Float weight = Float.parseFloat(attrs.get(1));
                    Color eyeColor = Color.valueOf(attrs.get(2));
                    Color hairColor = Color.valueOf(attrs.get(3));
                    Person author = new Person(name, weight, eyeColor, hairColor);
                    return new CommandCountLessAuthor(author);

                }

                if (arr[0].equals("execute") && arr[1].equals("script")) {
                    File path = new File(arr[2]);
                    scanner = new Scanner(path);
                    return new CommandExecute(path, scanner, database);
                }

                if (arr[0].equals("remove") && arr[1].equals("head")) {
                    return new CommandRemoveHead();
                }

                if (arr[0].equals("remove") && arr[1].equals("lower")) {
                    return new CommandRemoveLower();
                }

                if (arr[0].equals("remove")) {
                    Long id = Long.parseLong(arr[1]);
                    return new CommandRemoveById(id);
                }

                if (arr[0].equals("clear")) {
                    return new CommandClear();
                }

                if (arr[0].equals("maxbyname") ) {
                    return new CommandMaxByName();
                }

                if (arr[0].equals("TunedIdWorks") && arr[1].equals("fields")) {
                    return new CommandPrintTunedInWorks();
                }

                if (arr[0].equals("show")) {
                    return new CommandShow();
                }

                if (arr[0].equals("info")) {
                    return new CommandInfo();
                }

                if (arr[0].equals("head")) {
                    
                    return new CommandHead();
                }

                if (arr[0].equals("save")) {
                    return new CommandSave(this.database);
                }


                if (arr[0].equals("exit")) {
                    scanner.close();
                    return new CommandExite();
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {

            }
            
            System.out.println("Неправильный ввод комманды, попробуйте ещё раз.");
        }
    }

    public static LabWork labWorkConstruct(List<String> attrsLi, boolean auto) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date creationDate;
            Long id;
            if (auto) {
                id = max_id + 1;
                creationDate = new Date();
            } else {
                id = Long.parseLong(attrsLi.get(0));
                creationDate = dateFormat.parse(attrsLi.get(4));
            }
            String name = attrsLi.get(1);
            Coordinates coordinates = new Coordinates(Integer.parseInt(attrsLi.get(2)), Double.parseDouble(attrsLi.get(3)));
            Long minimalPoint = Long.parseLong(attrsLi.get(5));
            Long tunedInWorks = Long.parseLong(attrsLi.get(6));
            Difficulty difficulty = Difficulty.valueOf(attrsLi.get(7));
            String authName = attrsLi.get(8);
            Float weight = Float.parseFloat(attrsLi.get(9));
            Color eyeColor = Color.valueOf(attrsLi.get(10));
            Color hairColor = Color.valueOf(attrsLi.get(11));

            if (minimalPoint <= 0 || weight <= 0) {
                return null;
            }

            Person auth = new Person(authName, weight, eyeColor, hairColor);
            LabWork lab = new LabWork(id, name, coordinates, creationDate, minimalPoint, tunedInWorks, difficulty, auth);
            return lab;

        } catch (ParseException e) {
            System.out.println("Ошибка парсина даты.");
            return null;
        }

    }
}