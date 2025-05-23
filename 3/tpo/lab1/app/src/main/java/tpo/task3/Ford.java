package tpo.task3;

// Персонаж Ford, который может зажечь спичку и найти/переключить выключатель
public class Ford extends Character {
    private boolean foundSwitch;
    private boolean matchLit;

    public Ford() {
        super("Ford");
        this.foundSwitch = false;
        this.matchLit = false;
    }

    // Метод зажигания спички с учетом состояния окружающей среды
    public void igniteMatch(Match match, Environment env) {
        if (match.getState() == MatchState.UNLIT) {
            match.light();
            matchLit = true;
            // При зажигании спички изменяется состояние среды:
            // Если среда DARK -> SHADOWY, если SHADOWY -> DIM
            if (env.getAmbience() == Ambience.DARK) {
                env.setAmbience(Ambience.SHADOWY);
            } else {
                env.setAmbience(Ambience.DIM);
            }
        }
    }

    // Метод для поиска и переключения выключателя при соблюдении условий среды
    public void searchAndToggleSwitch(SwitchDevice sw, Environment env) {
        // Ford может обнаружить выключатель, если среда в состоянии DIM
        if ((env.getAmbience() == Ambience.DIM)) {
            foundSwitch = true;
        }
        if (foundSwitch) {
            sw.toggle();
            // В зависимости от состояния переключателя изменяется окружающая среда:
            // При включении: переход из DIM/SHADOWY в BRIGHT, если уже BRIGHT – в DAZZLING.
            // При выключении: возвращение к DIM.
            if (sw.isOn()) {
                if (env.getAmbience() == Ambience.DIM) {
                    env.setAmbience(Ambience.BRIGHT);
                } else {
                    env.setAmbience(Ambience.DAZZLING);
                }
            } else {
                env.setAmbience(Ambience.DIM);
            }
        }
    }
}

