package lab8.app.auth.commands;

import lab8.app.auth.User;
import lab8.app.database.DataBase;
import lab8.app.signals.Signal;

public class RegistrationCommand extends AuthCommand {

    @Override
    public Signal execute() {

        Signal resultSignal = new Signal();

        User newUser = DataBase.createUser(getUser());
        
        if (newUser == null) {
            resultSignal.setMessage("Пользователь с таким username уже существует.");
            resultSignal.setSucces(false);
        } else {
            resultSignal.setMessage("Пользователь " + newUser.getUsername() + " успешно создан.");
            resultSignal.setSucces(true);
        }

        return resultSignal;
    }
    
}
