package pokemons;

import attacks.PoisonJab;

public class Skuntank extends Stunky {
    public Skuntank(String name, int level) {
        super(name, level);
        this.setStats(103, 93, 67, 71, 61, 84);
        this.addMove(new PoisonJab());
    }
}
