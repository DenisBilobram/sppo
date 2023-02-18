package lab5.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Database {

    File storage;

    public Database() {
        String env = System.getenv("DB_PATH");
        this.storage = new File(env);
        try {
            if (storage.createNewFile()) {
                FileWriter fw = new FileWriter(storage);
                fw.write("<stack>\n</stack>");
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Отсутсвует файл с базой данных и не получается его создать.");
        }
    }
    
}