package org.itmo.lab3.inmaterials;

import org.itmo.lab3.exceptions.NullDescription;

public class Smell extends Inmaterial{
    
    protected String description;

    public Smell(String desc) {
        if (desc == null || "".equals(desc)) throw new NullDescription();
        this.description = desc;
    }

    public void describe() {
        System.out.println(this.description);
    }

    public void increase() {
        System.out.println(this.description + " smell increase...");
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object thing) {
        if (thing instanceof Smell) {
            return this.description == ((Smell)thing).description;
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
