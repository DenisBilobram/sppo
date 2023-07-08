package lab8.app.auth.commands;

import java.util.ResourceBundle;

import lab8.app.auth.User;
import lab8.app.database.DataBase;
import lab8.app.signals.Signal;

public class RegistrationCommand extends AuthCommand {

    @Override
    public Signal execute(ResourceBundle bundle) {

        Signal resultSignal = new Signal();

        User newUser = DataBase.createUser(getUser());
        
        if (newUser == null) {
            resultSignal.setMessage(bundle.getString("usalredy"));
            resultSignal.setSucces(false);
        } else {
            resultSignal.setSucces(true);
        }

        return resultSignal;
    }
    
}
