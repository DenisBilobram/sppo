package lab2.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import lab2.common.UnaryFunction;

public class CsvExporter {

    public static void exportToCsv(UnaryFunction function, double start, double end, double step, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("X, F(X)\n");
            for (double x = start; x <= end + 1e-12; x += step) {
                double y = function.apply(x);
                writer.write(String.format(Locale.US, "%.6f, %.6f\n", x, y));
            }
        }
    }
}
