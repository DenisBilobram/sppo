package app.commands.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.client.input.CommandParser;
import app.client.network.Reciever;
import app.client.network.Sender;
import app.client.network.ServerConnection;
import app.commands.Command;
import app.signals.CommandSignal;
import app.signals.Signal;
import app.signals.SignalManager;



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
                Command command = commandParser.recieveCommand(scanner);

                if (command == null) {
                    continue;
                }

                Signal executedCommand;
                if (command instanceof ClientCommand) {
                    executedCommand = ((ClientCommand)command).execute();
                } else {
                    CommandSignal signal = new CommandSignal(command);
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
            resultSignal.setMessage("Скрипт выполнен");
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
