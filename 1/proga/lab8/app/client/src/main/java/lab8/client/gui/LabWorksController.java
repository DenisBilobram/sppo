package lab8.client.gui;

import javafx.application.Platform;
import lab8.app.auth.User;
import lab8.app.commands.Command;
import lab8.app.commands.CommandShow;
import lab8.app.signals.ServerSignal;
import lab8.client.ClientApp;

public class LabWorksController extends Thread {

    
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
                return;
			}
            Command command = new CommandShow();
            User user = ClientApp.getUser();
            if (user == null) {
                return;
            } 
            command.setUser(ClientApp.getUser());
            ServerSignal serverSignal = ClientApp.getConnection().executeCommandOnServer(command, false);
            if (serverSignal == null) {
                continue;
            }

            if (!(ClientApp.getCurrentCollectLabWorks().containsAll(serverSignal.getPriorityBlockingQueue()) && serverSignal.getPriorityBlockingQueue().containsAll(ClientApp.getCurrentCollectLabWorks()))) {
                Platform.runLater(() -> ClientApp.updateMainPainWithLabWorks());
                Platform.runLater(() -> ClientApp.updateLabWorkTable());
            }

        }
            

    }

}
