package lab2;

import lab2.common.UnaryFunction;
import lab2.trig.*;
import lab2.log.*;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final double TOL = 1e-12;

    private static final Map<Integer, String> menu    = new LinkedHashMap<>();
    private static final Map<Integer, UnaryFunction> modules = new LinkedHashMap<>();

    static {

        UnaryFunction cos = new CosFunction(TOL);
        UnaryFunction sin = new SinFunction(cos);
        UnaryFunction tan = new TanFunction(sin, cos);
        UnaryFunction cot = new CotFunction(sin, cos);
        UnaryFunction sec = new SecFunction(cos);
        UnaryFunction csc = new CscFunction(sin);

        UnaryFunction ln    = new LnFunction(TOL);
        UnaryFunction log2  = new Log2Function(ln);
        UnaryFunction log3  = new Log3Function(ln);
        UnaryFunction log10 = new Log10Function(ln);

        UnaryFunction system = new SystemFunction(
                sin, cos, tan, cot, sec, csc,
                ln, log2, log3, log10
        );

        menu.put(1,  "sin(x)");
        menu.put(2,  "cos(x)");
        menu.put(3,  "tan(x)");
        menu.put(4,  "cot(x)");
        menu.put(5,  "sec(x)");
        menu.put(6,  "csc(x)");
        menu.put(7,  "ln(x)");
        menu.put(8,  "log2(x)");
        menu.put(9,  "log3(x)");
        menu.put(10, "log10(x)");
        menu.put(11, "System function");

        modules.put(1,  sin);
        modules.put(2,  cos);
        modules.put(3,  tan);
        modules.put(4,  cot);
        modules.put(5,  sec);
        modules.put(6,  csc);
        modules.put(7,  ln);
        modules.put(8,  log2);
        modules.put(9,  log3);
        modules.put(10, log10);
        modules.put(11, system);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            int    choice = readModuleChoice(sc);
            double step   = readStep(sc);
            String delim  = readDelimiter(sc);
            Path   file   = readFilePath(sc);

            UnaryFunction f       = modules.get(choice);
            String        caption = menu.get(choice);

            try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
                out.println("x" + delim + caption);
                for (double x = -Math.PI; x <= Math.PI + 1e-12; x += step) {
                    double y;
                    try {
                        y = f.apply(x);
                    } catch (Exception e) {
                        y = Double.NaN;
                    }
                    out.println(x + delim + y);
                }
            }
            System.out.println("File created successfully: " + file.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Execution failed: " + e.getMessage());
        }
    }


    private static int readModuleChoice(Scanner sc) {
        while (true) {
            System.out.println("Select a module to export:");
            menu.forEach((k, v) -> System.out.printf("%2d - %s%n", k, v));
            System.out.print("Your choice: ");
            String in = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(in);
                if (menu.containsKey(n)) return n;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid option. Try again.");
        }
    }

    private static double readStep(Scanner sc) {
        while (true) {
            System.out.print("Enter step (>0, e.g. 0.1): ");
            String in = sc.nextLine().trim();
            try {
                double s = Double.parseDouble(in.replace(',', '.'));
                if (s > 0 && s <= Math.PI) return s;
            } catch (NumberFormatException ignored) {}
            System.out.println("Step must be positive and not greater than π.");
        }
    }

    private static String readDelimiter(Scanner sc) {
        System.out.print("Delimiter (comma by default): ");
        String in = sc.nextLine();
        return in.isEmpty() ? "," : in.substring(0, 1);
    }

    private static Path readFilePath(Scanner sc) {
        while (true) {
            System.out.print("Output CSV file name (e.g. result.csv): ");
            String in = sc.nextLine().trim();
            if (in.isEmpty()) {
                System.out.println("File name cannot be empty.");
                continue;
            }
            Path p = Path.of(in);
            try {
                if (!Files.exists(p)) {
                    Files.createFile(p);
                }
                if (Files.isWritable(p)) return p;
            } catch (Exception ignored) {}
            System.out.println("Cannot create/write file. Specify another path.");
        }
    }
}
