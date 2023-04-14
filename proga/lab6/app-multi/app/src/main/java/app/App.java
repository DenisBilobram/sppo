/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package app;

import java.util.regex.Pattern;

import app.client.Client;
import app.server.Server;

public class App {

    public static void main(String[] args) {
        if (args.length == 1) {

            if (args[0].equals("client")) {

                new Client("localhost", 7777).startClient();

            } else if (args[0].equals("server")) {

                new Server(7777).startServer();

            }

        } else if (args.length == 2 && args[0].equals("server") && isPort(args[1])) {

            new Server(Integer.parseInt(args[1])).startServer();

        } else if (args.length == 3 && args[0].equals("client") && isPort(args[2])) {

            new Client(args[1], Integer.parseInt(args[2])).startClient();

        } else {
            System.out.println("Неверный формат аргументов. Доступные форматы:\nclient\nclient {ip} {port}\nserver\nserver {port}\n");
        }
    }

    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validate(final String ip) {
        if (ip.equals("localhost") || PATTERN.matcher(ip).matches()) {
            return true;
        }
        System.out.println("Неверный формат ip.");
        return false;
    }

    public static boolean isPort(String string) {
        try {
            Integer port = Integer.parseInt(string);
            if (port > 0 && port < 65536) {
                return true;
            }
        } catch (NumberFormatException exp) {
        }
        System.out.println("Неверный формат порта.");
        return false;
    }
}