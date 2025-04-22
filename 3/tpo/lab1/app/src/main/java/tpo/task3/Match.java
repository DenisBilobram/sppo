package tpo.task3;

// Класс, описывающий спичку с имитацией процесса горения
public class Match {
    private MatchState state;
    private int burnTime; // внутренний счетчик горения

    public Match() {
        this.state = MatchState.UNLIT;
        this.burnTime = 0;
    }

    public MatchState getState() {
        return state;
    }

    // Метод зажигания спички
    public void light() {
        state = MatchState.LIT;
        burnTime = 0;
    }

    // Метод обновления состояния спички. Если зажженная спичка горела слишком долго, она переходит в состояние BURNED.
    public void updateBurnTime(int timeUnits) {
        burnTime += timeUnits;
        if (burnTime > 5) { // пороговое значение для перехода в BURNED
            state = MatchState.BURNED;
        }
    }
}
