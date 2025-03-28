package tpo.task3;

// Абстрактный класс для сенсорных входов (ощущений, сигналов)
public abstract class SensoryInput {
    protected String description;

    public SensoryInput(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Метод обработки сенсорного входа
    public abstract void process();
}
