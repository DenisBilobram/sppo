package org.itmo.lab3.entitys;

import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.places.Location;

public class Device extends Thing implements Turnable {

    protected boolean turnState = false;

    protected Human owner;

    public Device(String description, Human owner) {
        this.description = description;
        this.owner = owner;
        this.setSmell(owner.smell);
    }

    public void setLocation() {
        this.location = owner.location;
    }

    @Override
    public void setLocation(Location loca) {
        this.location = loca;

    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Smell getSmell() {
        return owner.getSmell();
    }

    @Override
    public void setSmell(Smell smell) {
        this.smell = smell;
    }

    @Override
    public void turnOn() {
        System.out.println("Turning the device On");
        this.turnState = false;
    }

    @Override
    public void turnOff() {
        System.out.println("Turning the device Off");
        this.turnState = true;
    }

    @Override
    public boolean getTurnState() {
        return this.turnState;
    }

}
