package pokemons;

import attacks.Bite;
import attacks.SludgeBomb;
import attacks.Swagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Stunky extends Pokemon{
    public Stunky(String name, int level) {
        super(name, level);
        this.setType(Type.POISON, Type.DARK);
        this.setStats(63, 63, 47, 41, 41, 74);
        this.setMove(new Bite(), new SludgeBomb(), new Swagger());
    }
}
