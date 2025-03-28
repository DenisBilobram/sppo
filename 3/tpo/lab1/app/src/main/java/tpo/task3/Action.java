package tpo.task3;

// Абстрактный класс для действий (событий, активностей)
public abstract class Action {
    protected String description;

    public Action(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Метод выполнения действия
    public abstract void execute();
}
