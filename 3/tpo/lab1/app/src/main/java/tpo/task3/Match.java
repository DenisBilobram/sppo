package tpo.task3;

// Спичка как физический объект
public class Match extends PhysicalObject {
    public Match(String name) {
        super(name);
    }

    @Override
    public void interact() {
        System.out.println("Спичка используется для зажигания.");
    }
}
