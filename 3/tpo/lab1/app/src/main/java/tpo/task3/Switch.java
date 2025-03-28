package tpo.task3;

// Выключатель как физический объект
public class Switch extends PhysicalObject {
    public Switch(String name) {
        super(name);
    }

    @Override
    public void interact() {
        System.out.println("Выключатель переключается.");
    }
}
