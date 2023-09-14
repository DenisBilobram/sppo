package lab8.server.threads;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.RecursiveAction;

import lab8.app.commands.Command;
import lab8.app.labwork.Language;
import lab8.server.Server;
import lab8.server.network.Receiver;
import lab8.server.network.Sender;

public class SignalReceiveTask extends RecursiveAction{

    SocketChannel channel;
    Receiver receiver;
    Sender sender;

    public SignalReceiveTask(SelectionKey key) {
        this.channel = (SocketChannel)key.channel();
        key.cancel();
        this.receiver = new Receiver(channel);
        this.sender = new Sender(channel);
    }

    @Override
    protected void compute() {

        Command command = receiver.recieveCommands();

        if (command == null) {
            try {
                System.out.println("Потеряно соединение с клиентом " + channel.getRemoteAddress() + ".");
                channel.close();
                Server.getSession().remove(channel);
            } catch (IOException exp) {
                System.out.println("Ошибка во время получения команды.");
                exp.printStackTrace();
            }
            return;
        }

        System.out.println("Команда получена.");
        try {


            Locale locale = new Locale("ru");


            Language language = command.getUser() == null ? null : command.getUser().getLanguage();
                
            if (language == Language.ENGLISH) {
                locale = new Locale("en");
            } else if (language == Language.SERBIAN) {
                locale = new Locale("sr");
            }else if (language == Language.ALBANIAN) {
                locale = new Locale("alb");
            }

            ResourceBundle bundle = ResourceBundle.getBundle("locale/all", locale);

            CommandExecuteTask commandExecuteTask = new CommandExecuteTask(command, channel, bundle);

            Server.getExecutor().execute(commandExecuteTask);

        } catch (Exception exp) {
            exp.printStackTrace();
        }

        

    }
    
}
