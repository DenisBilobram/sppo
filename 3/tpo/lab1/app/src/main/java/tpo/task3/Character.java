package tpo.task3;

// Абстрактный класс для персонажей
public abstract class Character {
    protected String name;
    protected CharacterState state;

    public Character(String name) {
        this.name = name;
        this.state = CharacterState.STUNNED;
    }

    public String getName() {
        return name;
    }

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }
}
