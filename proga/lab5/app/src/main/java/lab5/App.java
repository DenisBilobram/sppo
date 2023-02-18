package lab5;

import java.util.PriorityQueue;

import lab5.commands.Command;
import lab5.commands.CommandExite;
import lab5.commands.Reciever;
import lab5.database.DataReader;
import lab5.database.Database;

public class App {

    public static void main(String[] args) {

        

        Database db = new Database();
        DataReader dr = new DataReader(db);

        PriorityQueue<LabWork> stack = dr.read();
        Long max_id = 0l;

        for (LabWork lab : stack) {
            if (lab.getId() > max_id) {
                max_id = lab.getId();
            }
        }
        
        Reciever rc = new Reciever(max_id, db);
        Command command = rc.commandRecive(null);

        while (!(command instanceof CommandExite)) {
            command.execute(stack);
            command = rc.commandRecive(null);
        }
        System.out.println(stack.size());
    }
}
