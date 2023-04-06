package app.signals;

public class SignalManager {
    
    public static void handle(Signal signal) {
        if (signal.isSucces()) {
            System.out.println("Команда выполена успешно.");
        } else {
            System.out.println("Не удалось выполнить команду");
        }
        System.out.print(signal.getMessage());
    }

}
