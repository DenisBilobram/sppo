package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class SludgeBomb extends SpecialMove {
    public SludgeBomb() {
        super(Type.POISON, 90, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.3);
        e.poison(p);
    }

    @Override
    protected String describe() {
        return "использует Sludge Bomb";
    }
}
