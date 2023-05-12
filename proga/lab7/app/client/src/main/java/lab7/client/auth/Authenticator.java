package lab7.client.auth;

import java.util.Scanner;

import lab7.app.auth.User;
import lab7.app.auth.commands.AuthCommand;
import lab7.app.auth.commands.LoginCommand;
import lab7.app.auth.commands.RegistrationCommand;
import lab7.app.input.LabWorkInput;
import lab7.app.signals.ClientSignal;
import lab7.app.signals.ServerSignal;
import lab7.app.signals.SignalManager;
import lab7.client.network.Sender;
import lab7.client.Client;
import lab7.client.network.Reciever;

public class Authenticator {

    Sender sender;
    Reciever receiver;
    boolean authenticated = false;

    User serverUser = null;

    public Authenticator(Sender sender, Reciever receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    
    public User authentication() {

        Scanner scanner = new Scanner(System.in);
        while (!authenticated) {

            User user = null;

            

            AuthCommand authCommand = LabWorkInput.getAuthOption(scanner);
            if (authCommand instanceof RegistrationCommand) {
                user = LabWorkInput.getRegister(scanner);
            } else if (authCommand instanceof LoginCommand) {
                user = LabWorkInput.getLogin(scanner);
            }

            if (user == null) {
                continue;
            } else {
                authCommand.setUser(user);
                serverUser = auth(authCommand);

                if (serverUser == null) {
                    if (!Client.getServer().checkConnectiion()) {
                        return null;
                    }
                }
            }
        }

        return serverUser;
        
    }

    public User auth(AuthCommand command) {

        ClientSignal signalToSend = new ClientSignal(command);
        sender.sendClientSignal(signalToSend);
        ServerSignal responseSignal = (ServerSignal)receiver.getServerSignal();
        if (responseSignal != null) {
            SignalManager.handle(responseSignal);
            if (responseSignal.isSucces()) {
                this.authenticated = true;
                return responseSignal.getUser();
            }
        } 
        return null;
    }
}
