package app.signals;

public class SignalManager {
    
    public static void handle(Signal signal) {
        if (signal.isSucces()) {
            System.out.println("Команда выполена успешно.\n");
        } else {
            System.out.println("Не удалось выполнить команду.\n");
        }
        System.out.println(signal.getMessage() + "\n");
    }

}
