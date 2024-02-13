package org.itmo.lab2.pokemons;

import org.itmo.lab2.attacks.Bite;
import org.itmo.lab2.attacks.SludgeBomb;
import org.itmo.lab2.attacks.Swagger;
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
