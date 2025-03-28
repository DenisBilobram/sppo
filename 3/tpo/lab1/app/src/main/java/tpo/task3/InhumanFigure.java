package tpo.task3;

// Нечеловеческая фигура
public class InhumanFigure extends PhysicalObject {
    public InhumanFigure(String name) {
        super(name);
    }

    @Override
    public void interact() {
        System.out.println("Нечеловеческая фигура вызывает тревогу.");
    }
}
