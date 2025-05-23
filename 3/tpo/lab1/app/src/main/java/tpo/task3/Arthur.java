package tpo.task3;

// Персонаж Arthur, который оценивает состояние окружающей среды и изменяет своё внутреннее состояние
public class Arthur extends Character {
    private boolean hasStoodUp;

    public Arthur() {
        super("Arthur");
        this.hasStoodUp = false;
    }

    // Arthur пытается встать, что изменяет его состояние на AWAKE
    public void standUp() {
        if (!hasStoodUp) {
            hasStoodUp = true;
            state = CharacterState.AWAKE;
        }
    }

    // Arthur анализирует окружающую среду и в зависимости от интенсивности запаха, гула и активности теней меняет своё состояние
    public void perceiveEnvironment(Environment env) {
        Ambience envAmbience = env.getAmbience();
        int agitationCount = 0;
        for (Shadow s : env.getShadows()) {
            if (s.getState() == ShadowState.AGITATED) {
                agitationCount++;
            }
        }
        if (envAmbience == Ambience.DARK
                || envAmbience == Ambience.DIM) {
            if (agitationCount > 1 || env.getSmellIntensity() > 6) {
                state = CharacterState.DISORIENTED;
            } else {
                state = CharacterState.CONFUSED;
            }
        } else if (envAmbience == Ambience.BRIGHT) {
            state = CharacterState.DETERMINED;
        }
    }
}
