package org.itmo.lab2.attacks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class TeeterDance extends StatusMove {
    public TeeterDance() {
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        p.confuse();
    }
    
    @Override
    protected String describe() {
        return "использует Teeter Dance";
    }
}
