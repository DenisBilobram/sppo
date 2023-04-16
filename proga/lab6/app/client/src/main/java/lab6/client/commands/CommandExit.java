package lab6.client.commands;

import lab6.app.signals.Signal;


/** Класс команды реализующей выход из программы. 
 * 
 */
public class CommandExit extends ClientCommand {

    public Signal execute() {

        System.exit(0);
        return new Signal("");
    }

    
}
