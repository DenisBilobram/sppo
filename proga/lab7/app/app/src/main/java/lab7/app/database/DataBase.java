package lab7.app.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import lab7.app.auth.User;
import lab7.app.labwork.Color;
import lab7.app.labwork.LabWork;
import lab7.app.labwork.Person;

/** Класс реализующий базу данных программы. Хранение данных происходит в файле db.json в формате json.
 * 
 */
public class DataBase {

    private static Connection dataBasConnection = null;

    public synchronized static Connection getDataBasConnection() {
        return dataBasConnection;
    }

    static {
        String url = "jdbc:postgresql://db:5432/studs";
        try {
            String cfgPath = System.getenv("DB_CFG");

            if (cfgPath == null) {
                System.out.println("ERROR: Нет переменной окружения DB_CFG.");
                System.exit(1);
            }

            Scanner scanner = new Scanner(new File(cfgPath));
            String user = scanner.nextLine().split("=")[1];
            String pass = scanner.nextLine().split("=")[1];

            DataBase.dataBasConnection = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: Ошбика при подключении к базе данных.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static synchronized LabWork createLabWork(LabWork labWork, User user) {
        try {
            user = readUserByUsername(user.getUsername());

            PreparedStatement statement = dataBasConnection.prepareStatement("INSERT INTO LABWORK (lab_name, x_coord, y_coord, creation_date, minimal_point, tuned_in_works, difficulty, user_id) VALUES (?, ?, ?, ?, ?, ?, ?::difficulty, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, labWork.getName());
            statement.setLong(2, labWork.getCoordinates().getX());
            statement.setLong(3, labWork.getCoordinates().getY());
            statement.setDate(4, new java.sql.Date(labWork.getCreationDate().getTime()));
            statement.setLong(5, labWork.getMinimalPoint());
            statement.setLong(6, labWork.getTunedInWorks());
            statement.setString(7, labWork.getDifficulty().toString());
            statement.setLong(8, user.getId());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            Long labWorkId = generatedKeys.getLong(1);
            labWork.setId(labWorkId);

            return labWork;

    
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized boolean updateLabWorkById(long id, LabWork labWork) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("UPDATE LABWORK SET lab_name=?, x_coord=?, y_coord=?, minimal_point=?, tuned_in_works=?, difficulty=?::difficulty WHERE id=?");
            statement.setString(1, labWork.getName());
            statement.setLong(2, labWork.getCoordinates().getX());
            statement.setLong(3, labWork.getCoordinates().getY());
            statement.setLong(4, labWork.getMinimalPoint());
            statement.setLong(5, labWork.getTunedInWorks());
            statement.setString(6, labWork.getDifficulty().toString());
            statement.setLong(7, id);

            statement.executeUpdate();

            return true;
        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
    }

    public static synchronized User readUserByUsername(String username) {

        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("SELECT username, password, id FROM APP_USER where username=?");

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User gotUser = new User(resultSet.getString(1), resultSet.getString(2), null);
                gotUser.setId(resultSet.getLong(3));
                return gotUser;
            } else {
                return null;
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized Person readProfileByUserId(long id) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("SELECT name, height, eyecolor FROM PERSON WHERE id=?");
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return new Person(resultSet.getString(1), resultSet.getFloat(2), Color.valueOf(resultSet.getString(3)));

        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized boolean deleteLabWorkById(long id) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("DELETE FROM LABWORK WHERE id=?");
            statement.setLong(1, id);

            statement.executeUpdate();
            return true;
        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean deleteAllLabWorks() {
        try {
            Statement statement = dataBasConnection.createStatement();

            statement.executeUpdate("TRUNCATE TABLE LABWORK");

            return true;

        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
    }
}


