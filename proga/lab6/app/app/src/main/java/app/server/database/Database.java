package app.server.database;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import app.labwork.LabWork;
import app.signals.Signal;

/** Класс реализующий базу данных программы. Хранение данных происходит в файле db.xml в формате xml.
 * 
 */
public class Database {

    private File storage;

    public File getStorage() {
        return storage;
    }

    public void setStorage(File storage) {
        this.storage = storage;
    }

    public Database() {
    }

    public Signal connect() {

        Signal resultSignal = new Signal();
        String env = System.getenv("DB_PATH");

        try  {
            this.storage = new File(env);
        } catch (NullPointerException exp) {
            resultSignal.setMessage("\nНет переменной окружения DB_PATH.");
            resultSignal.setSucces(false);
            return resultSignal;
        }
        
        try { 
            if (this.storage.exists() && !this.storage.isDirectory()) {

                resultSignal.setMessage("\nПодключился к базе данных.");
                resultSignal.setSucces(true);

            } else if (!this.storage.createNewFile()) {

                this.storage = new File("./db.json");
                
                if (this.storage.createNewFile() || this.storage.exists()) {
                    resultSignal.setMessage("\nИспользую ./db.json.");
                    resultSignal.setSucces(true);
                } else {
                    throw new IOException();
                }
            } else {
                resultSignal.setMessage(String.format("\nСоздал базу данных по адресу %s и подключился.", this.storage.getAbsolutePath()));
                resultSignal.setSucces(true);
            }
        } catch (IOException e) {
            resultSignal.setMessage("\nОтсутсвует файл с базой данных и не получается его создать.");
            resultSignal.setSucces(false);
        }
        return resultSignal;
    }

    public void save(PriorityQueue<LabWork> priorityQueue) {
        DataWriter dw = new DataWriter(this);
        dw.write(priorityQueue);
    }
}