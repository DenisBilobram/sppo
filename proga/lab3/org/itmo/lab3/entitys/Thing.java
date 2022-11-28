package org.itmo.lab3.entitys;


import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.places.Location;
import org.itmo.lab3.places.Smelling;

public abstract class Thing extends Entity implements Smelling{

    public ThingSizes size;

    public Location location;

    public Smell smell;

    public String description;

    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object thing) {
        if (thing instanceof Thing) {
            return this.description == ((Thing)thing).description;
        }
        return super.equals(thing);
    }
}
