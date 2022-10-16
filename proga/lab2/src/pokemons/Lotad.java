package pokemons;

import attacks.DoubleTeam;
import attacks.Scald;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Lotad extends Pokemon {
    public Lotad(String name, int level) {
        super(name, level);
        this.setType(Type.WATER, Type.GRASS);
        this.setStats(40, 30, 30, 40, 50, 30);
        this.setMove(new DoubleTeam(), new Scald());
    }
}