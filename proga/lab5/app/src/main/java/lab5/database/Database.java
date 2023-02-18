package lab5.database;

import java.io.File;

public class Database {

    File storage;

    public Database() {
        String env = System.getenv("DB_PATH");
        this.storage = new File(env);
    }
    
}
