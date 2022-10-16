package attacks;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 75, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        switch (def.getCondition()) {
            case BURN, POISON, PARALYZE:
                damage *= 2;
        }
        def.setMod(Stat.HP, (int)Math.round(damage));
    }


    @Override
    protected String describe() {
        return "использует Facade";
    }
}
