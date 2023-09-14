package org.itmo.lab3.entitys;

import org.itmo.lab3.exceptions.NullDescription;
import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.places.Location;

public class Device extends Thing implements Turnable {

    protected boolean broken = false;

    protected boolean turnState = false;

    protected Human owner;

    protected final int MAX_DURABILITY = 250;

    protected int durability = MAX_DURABILITY;

    protected Service service = new Service();

    protected class Service {

        protected int repairCost;

        Service() {
            repairCost = Device.this.MAX_DURABILITY / 10;
        }

        public void loseDurability() {
            if (Device.this.durability > 0) {
                Device.this.durability -= 1;
            }
            else {
                this.breakDown();
            }
        }

        public void repairDurability() {
            Device.this.durability = MAX_DURABILITY;
        }

        public void breakDown() {
            Device.this.broken = true;
        }
    }

    public Device(String description, Human owner) {
        if (description == null || "".equals(description)) throw new NullDescription();
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
        if (!this.broken) {
            this.service.loseDurability();
            this.turnState = true;
        }
    }

    @Override
    public void turnOff() {
        if (!this.broken) {
            this.turnState = false;
        } 
    }

    @Override
    public boolean getTurnState() {
        return this.turnState;
    }


}
