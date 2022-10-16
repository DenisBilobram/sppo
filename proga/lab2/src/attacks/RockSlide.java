package attacks;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.3);
        e.flinch(p);
    }
    @Override
    protected String describe() {
        return "использует Rock Slide";
    }
}
