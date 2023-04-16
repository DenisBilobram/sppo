package lab6.client.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab6.app.commands.Command;
import lab6.app.signals.ClientSignal;
import lab6.app.signals.Signal;
import lab6.app.signals.SignalManager;
import lab6.client.input.CommandParser;
import lab6.client.network.Reciever;
import lab6.client.network.Sender;
import lab6.client.network.ServerConnection;



/** Класс команды реализующей выполнение комманд из файла-скрипта. 
 * 
 */
public class CommandExecute extends ClientCommand {

    public CommandExecute() {
        this.requireFile = true;
    }

    private static List<File> filesList = new ArrayList<>();

    public Signal execute() {

        Signal resultSignal = new Signal();

        try {

            File file = getFile();

            if (filesList.contains(file)) {
                resultSignal.setMessage("В скрипте найдена рекурсия, завершаю выполнение.");
                resultSignal.setSucces(false);
                return resultSignal;
            }

            filesList.add(file);

            Scanner scanner = new Scanner(file);
            CommandParser commandParser = new CommandParser();

            Sender sender = new Sender(ServerConnection.getChannel());
            Reciever reciever = new Reciever(ServerConnection.getChannel());

            while (scanner.hasNextLine()) {
                Command command = commandParser.recieveCommand(scanner, false);

                if (command == null) {
                    continue;
                }

                Signal executedCommand;
                if (command instanceof ClientCommand) {
                    executedCommand = ((ClientCommand)command).execute();
                } else {
                    ClientSignal signal = new ClientSignal(command);
                    boolean sended = sender.sendCommandSignal(signal);
                    if (sended) {
                        executedCommand = reciever.getServerSignal();
                    } else {
                        executedCommand = new Signal("Не удалось отправить команду на сервер.");
                        executedCommand.setSucces(false);
                    }
                }
                SignalManager.handle(executedCommand);
            }
            resultSignal.setMessage("Конец скрипта.");
            resultSignal.setSucces(true);

            scanner.close();
            filesList.remove(file);

            return resultSignal;
        } catch (FileNotFoundException exp) {
            resultSignal.setMessage("Файл скрипта не найден.");
            resultSignal.setSucces(false);
        }
        return resultSignal;
    }
}
