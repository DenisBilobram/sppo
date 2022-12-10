package org.itmo.lab3.inmaterials;

public class Smell extends Inmaterial{
    
    protected String description;

    public Smell(String desc) {
        this.description = desc;
    }

    public void describe() {
        System.out.println("Smell of " + this.description);
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
