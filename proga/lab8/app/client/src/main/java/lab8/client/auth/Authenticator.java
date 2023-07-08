package lab8.client.auth;

import lab8.app.auth.User;
import lab8.app.auth.commands.AuthCommand;
import lab8.app.signals.ClientSignal;
import lab8.app.signals.ServerSignal;
import lab8.client.network.Sender;
import lab8.client.network.ServerConnection;
import lab8.client.ClientApp;
import lab8.client.network.Reciever;

public class Authenticator {

    Sender sender;
    Reciever receiver;
    boolean authenticated = false;

    User serverUser = null;

    public Authenticator(ServerConnection server) {
        this.sender = server.getSender();
        this.receiver = server.getReceiver();
    }
    
    public User authentication(AuthCommand authCommand) {
        ClientSignal signalToSend = new ClientSignal(authCommand);
        sender.sendClientSignal(signalToSend);
        ServerSignal responseSignal = (ServerSignal)receiver.getServerSignal();
        if (responseSignal != null) {
            if (!responseSignal.isSucces()) {
                ClientApp.createErrorStage(responseSignal.getMessage(), "Ок", event -> {
                    ClientApp.getErrorStage().close();
                }, false);
                return null;
            } else if (responseSignal.isSucces()) {
                this.authenticated = true;
                return responseSignal.getUser();
            }
        } else {
            ClientApp.setServer(new ServerConnection(ClientApp.getHost(), ClientApp.getPort()));
        }
        return null;
        
    }
}
