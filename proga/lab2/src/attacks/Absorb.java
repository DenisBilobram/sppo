package attacks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Absorb extends SpecialMove {
    public Absorb() {
        super(Type.GRASS, 20, 100);
    }
    
    @Override
    protected void applySelfEffects(Pokemon p) {
        double dmg = p.getStat(Stat.ATTACK);
        p.setMod(Stat.HP, (int)Math.round(dmg)*(-1));
    }
    @Override
    protected String describe() {
        return "использует Absorb";
    }
}
