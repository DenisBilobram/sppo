package pokemons;

import attacks.Facade;
import attacks.RockSlide;
import attacks.ShadowBall;
import attacks.TeeterDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Spinda extends Pokemon {
    public Spinda(String name, int level) {
        super(name, level);
        this.addType(Type.NORMAL);
        this.setStats(60, 60, 60, 60, 60, 60);
        this.setMove(new RockSlide(), new Facade(), new TeeterDance(), new ShadowBall());
    }
}