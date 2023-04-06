package server.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import server.labwork.LabWork;


/** Класс реализующий чтение данных из базы данных.
 * 
 */
public class DataReader {
    private Database database;

    public DataReader(Database db) {
        this.database = db;
    }

    public PriorityQueue<LabWork> read() {
        Gson gson = new Gson();
        try {
            BufferedReader stream = new BufferedReader(new FileReader(this.database.getStorage()));
            if (stream.read() == -1) {
                stream.close();
                return new PriorityQueue<LabWork>();
            }
            stream.close();
            stream = new BufferedReader(new FileReader(this.database.getStorage()));
            Type priorityQueue = new TypeToken<PriorityQueue<LabWork>>() {}.getType();
            PriorityQueue<LabWork> outpuPriorityQueue = gson.fromJson(stream, priorityQueue);
            if (outpuPriorityQueue != null) {
                List<Long> idList = new ArrayList<>();
                List<Long> uniqeId = new ArrayList<>();
                for (LabWork labWrok : outpuPriorityQueue) {
                    idList.add(labWrok.getId());
                }
                for (Long id : idList) {
                    if (uniqeId.contains(id)) {
                        System.out.println("Неуникальное поле ID в базе данных.");
                        System.exit(0);
                    } else {
                        uniqeId.add(id);
                    }
                }
            }
            return outpuPriorityQueue;

        } catch (IOException exp) {
            System.out.println("База данных недоступна.");
            
            return new PriorityQueue<LabWork>();
        } catch (JsonSyntaxException exp) {
            System.out.println("Ошибка во время чтения из базы данных.");
            return new PriorityQueue<LabWork>();
        }
        
    } 
}
