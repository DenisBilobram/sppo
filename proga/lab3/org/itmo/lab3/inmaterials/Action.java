package org.itmo.lab3.inmaterials;

public class Action extends Inmaterial{
    
    public String description;

    public Action(String desc) {
        this.description = desc;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
