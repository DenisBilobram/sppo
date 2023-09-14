package org.itmo.lab2.pokemons;


import org.itmo.lab2.attacks.Facade;
import org.itmo.lab2.attacks.RockSlide;
import org.itmo.lab2.attacks.ShadowBall;
import org.itmo.lab2.attacks.TeeterDance;
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