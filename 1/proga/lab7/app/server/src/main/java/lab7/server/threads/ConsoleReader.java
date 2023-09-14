package lab7.server.threads;

import java.nio.channels.SocketChannel;
import java.util.Scanner;

import lab7.server.Server;

public class ConsoleReader extends Thread {

    public ConsoleReader() {
        super();
    }

    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
        
            while (true) {
                String command = scanner.nextLine();
                if (command.equals("exit")) {
                    scanner.close();
                    for (SocketChannel channel : Server.getSession()) {
                        channel.close();
                    }
                    System.exit(0);
                } else {
                    System.out.println("Введите exit для выхода.");
                }
            
            }
        } catch (Exception exp) {
            System.exit(0);
        }
        
    }
}
