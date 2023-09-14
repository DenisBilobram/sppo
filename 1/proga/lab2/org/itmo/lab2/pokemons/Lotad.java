package org.itmo.lab2.pokemons;


import org.itmo.lab2.attacks.DoubleTeam;
import org.itmo.lab2.attacks.Scald;
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