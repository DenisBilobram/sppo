import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Spinda p1 = new Spinda("Крол", 1);
        Stunky p2 = new Stunky("Енот", 1);
        Skuntank p3 = new Skuntank("Ёж", 1);
        Lotad p4 = new Lotad("Кувшинка", 1);
        Lombre p5 = new Lombre("Голем", 1);
        Ludicolo p6 = new Ludicolo("Шишка", 1);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
