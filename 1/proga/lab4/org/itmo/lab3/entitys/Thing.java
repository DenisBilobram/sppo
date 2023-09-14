package org.itmo.lab3.entitys;


import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.places.Location;
import org.itmo.lab3.places.Smelling;

public abstract class Thing extends Entity implements Smelling{

    protected ThingSizes size;

    protected Location location;

    protected Smell smell;

    protected String description;

    @Override
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

    @Override
    public int hashCode() {
        int result = this.getClass().hashCode();
        result = result + 31 * (this.description != null ? this.description.hashCode() : 0);
        return result;
    }
}
