package lab7.app.auth.commands;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lab7.app.auth.User;
import lab7.app.database.DataBase;
import lab7.app.labwork.Person;
import lab7.app.signals.Signal;

public class RegistrationCommand extends AuthCommand {

    @Override
    public Signal execute() {

        Signal resultSignal = new Signal();
    
        try {
            PreparedStatement statement = DataBase.getDataBasConnection().prepareStatement("SELECT EXISTS (SELECT * FROM APP_USER WHERE username=?)");
            statement.setString(1, getUser().getUsername());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            
            if (!resultSet.getBoolean(1)) {
                PreparedStatement statementInsertPerson = DataBase.getDataBasConnection().prepareStatement("INSERT INTO PERSON (name, height, eyecolor) VALUES (?, ?, ?::color)", Statement.RETURN_GENERATED_KEYS);
                User user = getUser();
                Person profile = user.getProfile();
                statementInsertPerson.setString(1, profile.getName());
                statementInsertPerson.setFloat(2, profile.getHeigth());
                statementInsertPerson.setString(3, profile.getEyeColor().toString());

                statementInsertPerson.executeUpdate();
                
                ResultSet generatedKResultSetPerson = statementInsertPerson.getGeneratedKeys();
                generatedKResultSetPerson.next();

                long profileId = generatedKResultSetPerson.getLong(1);

                PreparedStatement statementInsertUser = DataBase.getDataBasConnection().prepareStatement("INSERT INTO APP_USER (username, password, person_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                statementInsertUser.setString(1, user.getUsername());

                String password = user.getPassword();

                password = "8ak34$6%" + password + password.length()*145;

                byte[] passwordByte = MessageDigest.getInstance("MD5").digest(password.getBytes());

                StringBuilder hashString = new StringBuilder();
                for (byte b : passwordByte) {
                    hashString.append(String.format("%02x", b));
                }

                statementInsertUser.setString(2, hashString.toString());
                statementInsertUser.setLong(3, profileId);

                statementInsertUser.executeUpdate();

                ResultSet generatedKResultSetUser = statementInsertUser.getGeneratedKeys();
                generatedKResultSetUser.next();

                long userId = generatedKResultSetUser.getLong(1);
                getUser().setId(userId);
                
                resultSignal.setMessage(String.format("Пользователь %s создан успешно.", user.getUsername()));
                resultSignal.setSucces(true);

            } else {
                resultSignal.setMessage("Пользователь с таким username уже существует.");
                resultSignal.setSucces(false);
            }

        } catch (SQLException | NoSuchAlgorithmException e) {
            resultSignal.setMessage("Не удалось создать пользователя по техническим причинам.");
            resultSignal.setSucces(false);
            e.printStackTrace();
        }

        return resultSignal;
    }
    
}
