package lab7.app.auth.commands;

import lab7.app.signals.Signal;

public class LogoutCommand extends AuthCommand{

    @Override
    public Signal execute() {
    
        Signal responsSignal = new Signal("Выход из аккаунта выполнен успешно.");
        responsSignal.setSucces(true);
        return responsSignal;
    }
    
}
