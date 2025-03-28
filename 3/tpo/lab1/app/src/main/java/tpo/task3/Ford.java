package tpo.task3;

public class Ford extends Character {
    public Ford(String name) {
        super(name);
    }

    @Override
    public void performAction(Action action) {
        System.out.println(getName() + " начинает действие: " + action.getDescription());
        action.execute();
    }

    @Override
    public void perceive(SensoryInput input) {
        System.out.println(getName() + " воспринимает: " + input.getDescription());
        input.process();
    }
}
