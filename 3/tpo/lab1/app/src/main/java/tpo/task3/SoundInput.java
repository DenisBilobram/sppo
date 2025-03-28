package tpo.task3;

// Сенсорный вход, связанный со звуком
public class SoundInput extends SensoryInput {
    public SoundInput(String description) {
        super(description);
    }

    @Override
    public void process() {
        System.out.println("Обработка звуков: " + description);
    }
}
