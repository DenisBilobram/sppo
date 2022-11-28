package org.itmo.lab3.inmaterials;

public class Smell extends Inmaterial{
    public String description;

    public Smell(String desc) {
        this.description = desc;
    }

    public void increase() {
        System.out.println(this.description + " smell increase...");
    }

    @Override
    public String toString() {
        return this.description;
    }
}
