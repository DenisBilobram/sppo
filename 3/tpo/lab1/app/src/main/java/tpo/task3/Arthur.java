package tpo.task3;

public class Arthur extends Character {
    public Arthur(String name) {
        super(name);
    }

    @Override
    public void performAction(Action action) {
        System.out.println(getName() + " выполняет действие: " + action.getDescription());
        action.execute();
    }

    @Override
    public void perceive(SensoryInput input) {
        System.out.println(getName() + " ощущает: " + input.getDescription());
        input.process();
    }
}
