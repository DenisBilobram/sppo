package org.itmo.lab3.places;

public class Location {

    public String description;

    public boolean available;

    public Location(String desc) {
        this.description = desc;
    }

    public boolean ableToRich() {
        return this.available;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
