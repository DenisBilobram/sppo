package tpo.task3;

// Сенсорный вход, связанный с визуальным восприятием (например, теней или фигур)
public class VisualInput extends SensoryInput {
    public VisualInput(String description) {
        super(description);
    }

    @Override
    public void process() {
        System.out.println("Обработка визуальных стимулов: " + description);
    }
}
