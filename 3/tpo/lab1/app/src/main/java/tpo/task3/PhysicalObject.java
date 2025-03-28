package tpo.task3;

// Абстрактный класс для физических объектов (материальные предметы и образы)
public abstract class PhysicalObject extends Entity {
    public PhysicalObject(String name) {
        super(name);
    }

    // Метод, описывающий физическое взаимодействие объекта
    public abstract void interact();
}
