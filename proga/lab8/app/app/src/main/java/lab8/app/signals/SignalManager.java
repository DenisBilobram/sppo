package lab8.app.signals;

public class SignalManager {

    public static void handle(Signal signal) {
        String output;
        if (signal.isSucces()) {
            
        } else {
            output = "Не удалось выполнить команду.";
        }
        
        
        output = signal.getMessage();
        if (output.equals("") || output == null) {
            return;
        }
        
    }

    public static void printMessage(String output, boolean lineSeparator) {

        for (int i = 0; i < output.length(); i++) {
            System.out.print(output.charAt(i));
        }

        if (lineSeparator) {
            System.out.print(System.lineSeparator());
        }   
        
    }

}
