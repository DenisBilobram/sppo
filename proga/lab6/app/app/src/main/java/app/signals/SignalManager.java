package app.signals;

public class SignalManager {
    
    public static void handle(Signal signal) {
        String output;
        if (signal.isSucces()) {
            output = "Команда выполена успешно.";
        } else {
            output = "Не удалось выполнить команду.";
        }
        
        printMessage(output, true);
        System.out.println();
        
        output = signal.getMessage();
        
        printMessage(output, true);
        System.out.println();
    }

    public static void printMessage(String output, boolean lineSeparator) {

        Long timeLong = 10l;

        if (output.length() > 100) timeLong = 1l;

        for (int i = 0; i < output.length(); i++) {
            System.out.print(output.charAt(i));
            try {
                Thread.sleep(timeLong);
            } catch (InterruptedException e) {
            }
        }

        if (lineSeparator) {
            System.out.print(System.lineSeparator());
        }
        
    }

}
