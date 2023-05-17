package lab7.app.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;

import lab7.app.labwork.Color;
import lab7.app.labwork.Coordinates;
import lab7.app.labwork.Difficulty;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;

public class DataSync {
    public static PriorityBlockingQueue<LabWork> getQueueFromDataBase(Connection dataBasConnection) {
        try (Statement statement = dataBasConnection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM APP_LABWORKS INNER JOIN APP_PERSONS ON APP_LABWORKS.user_id = APP_PERSONS.id");

             PriorityBlockingQueue<LabWork> priorityBlockingQueue = new PriorityBlockingQueue<>();

            while (result.next()) {

                String name = result.getString("lab_name");
                Coordinates coordinates = new Coordinates(result.getLong("x_coord"), result.getLong("y_coord"));
                Date creationDate = result.getDate("creation_date");
                long minimalPoint = result.getLong("minimal_point");
                long tunedInWorks = result.getLong("tuned_in_works");
                Difficulty difficulty = Difficulty.valueOf(result.getString("difficulty"));
                long id = result.getLong("id");

                Person auhor = new Person(result.getString("name"), result.getFloat("height"), Color.valueOf(result.getString("eyecolor")));

                LabWork labWork = new LabWork(name, coordinates, creationDate, minimalPoint, tunedInWorks, difficulty, auhor);
                labWork.setId(id);

                priorityBlockingQueue.add(labWork);

            }

            return priorityBlockingQueue;

        } catch (SQLException e) {
            System.out.println("ERROR: Ошибка при извлечении коллекции из БД.");
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
}