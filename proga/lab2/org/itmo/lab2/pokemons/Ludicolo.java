package org.itmo.lab2.pokemons;


import org.itmo.lab2.attacks.Swagger;

public class Ludicolo extends Lombre {
    public Ludicolo(String name, int level) {
        super(name, level);
        this.setStats(80, 70, 70, 90, 100, 70);
        this.addMove(new Swagger());
    }
}
