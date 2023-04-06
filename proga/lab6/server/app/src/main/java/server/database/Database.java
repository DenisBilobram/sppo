package server.database;

import java.io.File;
import java.io.IOException;

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
        String env = System.getenv("DB_PATH");
        try  {
            this.storage = new File(env);
        } catch (NullPointerException exp) {
            System.out.println("Нет переменной окружения DB_PATH.");
            System.exit(0);
        }
        
        try { 
            if (this.storage.exists() && !this.storage.isDirectory()) {
                return;
            }
            if (!this.storage.createNewFile() || this.storage.isDirectory()) {
                System.out.println("Нет доступа или неверный путь к базе данных. Пробую использовать файл по адресу ./db.json");
                this.storage = new File("./db.json");
                
                if (this.storage.createNewFile() || this.storage.exists()) {
                    System.out.println("Использую ./db.json.");
                } else {
                    System.out.println("Не удалось.");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Отсутсвует файл с базой данных и не получается его создать.");
        }
    }
    
}