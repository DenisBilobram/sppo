package lab5.commands;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Scanner;

import lab5.LabWork;
import lab5.database.Database;

public class CommandExecute implements Command {

    private File file;
    private Scanner scanner;
    private Database database;

    public CommandExecute(File f, Scanner scann, Database db) {
        this.file = f;
        this.scanner = scann;
        this.database = db;
    }

    @Override
    public boolean execute(PriorityQueue<LabWork> colleStack) {
        Reciever rc = new Reciever(Reciever.max_id, this.file, this.database);
        Command command = rc.commandRecive(this.scanner);
        while (!(command instanceof CommandExite) && !(command instanceof RepeatCommand)) {
            command.execute(colleStack);
            command = rc.commandRecive(this.scanner);
        }
        return true;
    }
}
