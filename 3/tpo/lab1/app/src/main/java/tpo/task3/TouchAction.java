package tpo.task3;

// Действие тактильного восприятия (ощупывание себя)
public class TouchAction extends Action {
    private Character character;

    public TouchAction(Character character) {
        super("Ощупывание себя");
        this.character = character;
    }

    @Override
    public void execute() {
        System.out.println(character.getName() + " ощупывает себя, пытаясь понять свое состояние.");
    }
}
