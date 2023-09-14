package org.itmo.lab3.places;

import org.itmo.lab3.exceptions.NullDescription;

public class Location {

    protected String description;

    protected boolean available;

    public Location(String desc) {
        if (desc == null || "".equals(desc)) throw new NullDescription();
        this.description = desc;
    }

    public void describe() {
        System.out.println(this);
    }

    public boolean ableToRich() {
        return this.available;
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object thing) {
        if (thing instanceof Location) {
            return this.description == ((Location)thing).description;
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
