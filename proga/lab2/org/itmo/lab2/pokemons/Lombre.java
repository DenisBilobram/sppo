package org.itmo.lab2.pokemons;

import org.itmo.lab2.attacks.Absorb;

public class Lombre extends Lotad {
    public Lombre(String name, int level) {
        super(name, level);
        this.setStats(60, 50, 50, 60, 70, 50);
        this.addMove(new Absorb());
    }
}
