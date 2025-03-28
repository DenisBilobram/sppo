package tpo.task3;

// Действие зажигания спички
public class IgniteMatchAction extends Action {
    private Match match;

    public IgniteMatchAction(Match match) {
        super("Зажечь спичку для поиска выключателя");
        this.match = match;
    }

    @Override
    public void execute() {
        System.out.println("Используется " + match.getName());
        match.interact();
    }
}
