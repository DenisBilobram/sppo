package tpo.task3;

// Абстрактный класс для живых существ и персонажей
public abstract class Character extends Entity {
    public Character(String name) {
        super(name);
    }

    // Каждый персонаж может выполнять действия
    public abstract void performAction(Action action);

    // Каждый персонаж может воспринимать сенсорные стимулы (запах, звук, визуальные образы и т.д.)
    public abstract void perceive(SensoryInput input);
}
