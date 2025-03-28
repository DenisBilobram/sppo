package tpo.task3;

// Действие движения (например, персонаж встает)
public class MoveAction extends Action {
    private Character character;

    public MoveAction(Character character) {
        super("Движение персонажа");
        this.character = character;
    }

    @Override
    public void execute() {
        System.out.println(character.getName() + " пытается встать на ноги.");
    }
}
