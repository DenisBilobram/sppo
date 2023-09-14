package org.itmo.lab2.pokemons;


import org.itmo.lab2.attacks.PoisonJab;

public class Skuntank extends Stunky {
    public Skuntank(String name, int level) {
        super(name, level);
        this.setStats(103, 93, 67, 71, 61, 84);
        this.addMove(new PoisonJab());
    }
}
