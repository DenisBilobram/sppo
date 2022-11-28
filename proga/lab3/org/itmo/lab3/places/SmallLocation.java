package org.itmo.lab3.places;

import org.itmo.lab3.inmaterials.Smell;

public class SmallLocation extends Location implements Smelling{

    public Smell smell;

    public SmallLocation(String desc) {
        super(desc);
    }

    @Override
    public Smell getSmell() {
        return smell;
    }

    @Override
    public void setSmell(Smell smell) {
        this.smell = smell;
    }

}
