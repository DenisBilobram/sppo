package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PoisonJab extends PhysicalMove {
    public PoisonJab() {
        super(Type.POISON, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.3);
        e.poison(p);
    }

    @Override
    protected String describe() {
        return "использует Poison Jab";
    }
}

