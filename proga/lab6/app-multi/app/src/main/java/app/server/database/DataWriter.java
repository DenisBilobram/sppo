package app.server.database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

import com.google.gson.Gson;

import app.labwork.LabWork;


/** Класс реализующий запись данных в базу данных.
 * 
 */
public class DataWriter {
    private Database database;

    public DataWriter(Database db) {
        this.database = db;
    }

    public void write(PriorityQueue<LabWork> priorityQueue) {
        Gson gson = new Gson();
        String jsoString = gson.toJson(priorityQueue);
        try {
            FileWriter fileWriter = new FileWriter(this.database.getStorage());
            fileWriter.write(jsoString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException exp) {
            System.out.println("Не получилось записать данные в базу данных.");
        } 
        

    }
}
