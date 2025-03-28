package tpo.task3;

// Тень как физический объект (может быть анимирована как визуальный образ)
public class Shadow extends PhysicalObject {
    public Shadow(String name) {
        super(name);
    }

    @Override
    public void interact() {
        System.out.println("Тень мелькает и движется.");
    }
}
