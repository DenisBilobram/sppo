package lab7.app.database;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

/** Класс реализующий базу данных программы.
 * 
 */
public class DataBase {

    private static Connection dataBasConnection = null;

    public synchronized static Connection getDataBasConnection() {
        return dataBasConnection;
    }

    static {
        String url = "jdbc:postgresql://localhost:5432/studs";
        // String url = "jdbc:postgresql://localhost:5432/app";
        try {

            Scanner scanner = new Scanner(new File("/home/studs/s367893/.pgpass"));
            String line = scanner.nextLine();
            String[] data = line.split(":");


            DataBase.dataBasConnection = DriverManager.getConnection(url, data[3], data[4]);

            // DataBase.dataBasConnection = DriverManager.getConnection(url, "postgres", "postgres");
        } catch (Exception e) {
            System.out.println("ERROR: Ошбика при подключении к базе данных.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static synchronized LabWork createLabWork(LabWork labWork, User user) {
        try {
            user = readUserByUsername(user.getUsername());

            PreparedStatement statement = dataBasConnection.prepareStatement("INSERT INTO APP_LABWORKS (lab_name, x_coord, y_coord, creation_date, minimal_point, tuned_in_works, difficulty, user_id) VALUES (?, ?, ?, ?, ?, ?, ?::difficulty, ?)", Statement.RETURN_GENERATED_KEYS);
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

    public static synchronized User createUser(User user) {
        try {

            PreparedStatement statement = DataBase.getDataBasConnection().prepareStatement("SELECT EXISTS (SELECT * FROM APP_USERS WHERE username=?)");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            
            if (!resultSet.getBoolean(1)) {
                
                Person profile = createPerson(user.getProfile());

                PreparedStatement statementInsertUser = DataBase.getDataBasConnection().prepareStatement("INSERT INTO APP_USERS (username, password, person_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                statementInsertUser.setString(1, user.getUsername());

                String password = user.getPassword();

                password = "8ak34$6%" + password + password.length()*145;

                byte[] passwordByte;
                passwordByte = MessageDigest.getInstance("MD2").digest(password.getBytes());
                

                StringBuilder hashString = new StringBuilder();
                for (byte b : passwordByte) {
                    hashString.append(String.format("%02x", b));
                }

                statementInsertUser.setString(2, hashString.toString());
                statementInsertUser.setLong(3, profile.getId());

                statementInsertUser.executeUpdate();

                ResultSet generatedKResultSetUser = statementInsertUser.getGeneratedKeys();
                generatedKResultSetUser.next();

                long userId = generatedKResultSetUser.getLong(1);
                user.setId(userId);
                
                return user;

            } else {
                return null;
            }
        
        } catch (SQLException | NoSuchAlgorithmException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized Person createPerson(Person person) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("INSERT INTO APP_PERSONS (name, height, eyecolor) VALUES (?, ?, ?::color)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setFloat(2, person.getHeigth());
            statement.setString(3, person.getEyeColor().toString());

            statement.executeUpdate();

            ResultSet generatedResultSet = statement.getGeneratedKeys();
            if (generatedResultSet.next()) {
                Long personid = generatedResultSet.getLong(1);
                person.setId(personid);
                return person;
            } else {
                return null;
            }

        
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized boolean updateLabWorkById(long id, LabWork labWork) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("UPDATE APP_LABWORKS SET lab_name=?, x_coord=?, y_coord=?, minimal_point=?, tuned_in_works=?, difficulty=?::difficulty WHERE id=?");
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
            PreparedStatement statement = dataBasConnection.prepareStatement("SELECT username, password, id FROM APP_USERS where username=?");

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User gotUser = new User(resultSet.getString(1), resultSet.getString(2), null);
                gotUser.setId(resultSet.getLong(3));

                Person profile = readProfileByUserId(gotUser.getId());
                gotUser.setProfile(profile);

                return gotUser;
            } else {
                return null;
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized String readUserNameByProfileId(long id) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("SELECT username FROM APP_PERSONS INNER JOIN APP_USERS ON APP_PERSONS.id = APP_USERS.person_id WHERE person_id=?");

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString(1);
                
                return username;
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

            PreparedStatement statement = dataBasConnection.prepareStatement("SELECT person_id FROM APP_USERS WHERE id=?");
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long personId = resultSet.getLong(1);

                statement = dataBasConnection.prepareStatement("SELECT name, height, eyecolor FROM APP_PERSONS WHERE id=?");
                statement.setLong(1, personId);

                resultSet = statement.executeQuery();
                resultSet.next();

                Person gotPerson = new Person(resultSet.getString(1), resultSet.getFloat(2), Color.valueOf(resultSet.getString(3)));
                gotPerson.setId(personId);

                return gotPerson;
            } else {
                return null;
            }

            

        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
    }

    public static synchronized boolean deleteLabWorkById(long id) {
        try {
            PreparedStatement statement = dataBasConnection.prepareStatement("DELETE FROM APP_LABWORKS WHERE id=?");
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

            statement.executeUpdate("TRUNCATE TABLE APP_LABWORKS RESTART IDENTITY");

            return true;

        } catch (SQLException exp) {
            exp.printStackTrace();
            return false;
        }
    }
}


