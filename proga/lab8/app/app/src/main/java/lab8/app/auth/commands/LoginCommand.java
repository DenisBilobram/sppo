package lab8.app.auth.commands;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lab8.app.auth.User;
import lab8.app.database.DataBase;
import lab8.app.labwork.Person;
import lab8.app.signals.Signal;

public class LoginCommand extends AuthCommand{

    @Override
    public Signal execute() {
        
        Signal resultSignal = new Signal();

        try {
            User trueUser = DataBase.readUserByUsername(getUser().getUsername());

            if (trueUser != null) {
                String truePasswordHash = trueUser.getPassword();

                String password = getUser().getPassword();

                password = "8ak34$6%" + password + password.length()*145;

                byte[] passwordByte = MessageDigest.getInstance("MD2").digest(password.getBytes());

                StringBuilder userHashPassword = new StringBuilder();
                for (byte b : passwordByte) {
                    userHashPassword.append(String.format("%02x", b));
                }

                if (userHashPassword.toString().equals(truePasswordHash)) {

                    getUser().setId(trueUser.getId());
                    
                    Person profile = DataBase.readProfileByUserId(trueUser.getId());
                    getUser().setProfile(profile);

                    resultSignal.setSucces(true);
                    resultSignal.setMessage("Авторизация выполнена успешно.");
                } else {
                    resultSignal.setSucces(false);
                    resultSignal.setMessage("Неверный password.");
                }

            } else {
                resultSignal.setSucces(false);
                resultSignal.setMessage("Пользователя с таким username не существует.");
            }

        } catch (NoSuchAlgorithmException exp) {
            resultSignal.setSucces(false);
            resultSignal.setMessage("Ошибка авторизаци.");
        }

        return resultSignal;
        
    }
    
}
