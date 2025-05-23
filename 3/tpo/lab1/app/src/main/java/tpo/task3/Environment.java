package tpo.task3;

import java.util.ArrayList;
import java.util.List;

// Описывает окружающую среду и её влияние на тени
public class Environment {
    private Ambience ambience;
    private int smellIntensity; // Интенсивность неприятного запаха (0-10)
    private int humLevel;       // Уровень гула (0-10)
    private List<Shadow> shadows;

    public Environment() {
        this.ambience = Ambience.DARK;
        this.smellIntensity = 7;
        this.humLevel = 5;
        this.shadows = new ArrayList<>();
        // Инициализируем несколько теней
        for (int i = 0; i < 3; i++) {
            shadows.add(new Shadow());
        }
        updateShadowStates();
    }

    public Ambience getAmbience() {
        return ambience;
    }

    // При смене освещенности автоматически обновляются состояния теней
    public void setAmbience(Ambience ambience) {
        this.ambience = ambience;
        updateShadowStates();
    }

    public int getSmellIntensity() {
        return smellIntensity;
    }

    public void setSmellIntensity(int smellIntensity) {
        this.smellIntensity = smellIntensity;
        updateShadowStates();
    }

    public int getHumLevel() {
        return humLevel;
    }

    public void setHumLevel(int humLevel) {
        this.humLevel = humLevel;
        updateShadowStates();
    }

    public List<Shadow> getShadows() {
        return shadows;
    }

    // Обновление состояния теней в зависимости от текущей атмосферы и условий
    public void updateShadowStates() {
        for (Shadow shadow : shadows) {
            switch (ambience) {
                case DARK:
                    shadow.setState(ShadowState.MOVING);
                    break;
                case SHADOWY:
                    // В зависимости от дополнительных условий тень может начать танцевать
                    if (smellIntensity > 5) {
                        shadow.setState(ShadowState.DANCING);
                    } else {
                        shadow.setState(ShadowState.MOVING);
                    }
                    break;
                case DIM:
                    // Если уровень запаха или гула высокий, тень становится возбужденной
                    if (smellIntensity > 6 || humLevel > 6) {
                        shadow.setState(ShadowState.AGITATED);
                    } else {
                        shadow.setState(ShadowState.MOVING);
                    }
                    break;
                case BRIGHT:
                case DAZZLING:
                    // При ярком свете тени успокаиваются
                    shadow.setState(ShadowState.STATIC);
                    break;
            }
        }
    }
}
