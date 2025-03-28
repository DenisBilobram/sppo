package tpo.task3;

// Сенсорный вход, связанный с запахом
public class SmellInput extends SensoryInput {
    public SmellInput(String description) {
        super(description);
    }

    @Override
    public void process() {
        System.out.println("Обработка запаха: " + description);
    }
}
