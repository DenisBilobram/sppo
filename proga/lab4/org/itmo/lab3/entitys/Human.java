package org.itmo.lab3.entitys;

import java.util.ArrayList;
import java.util.List;

import org.itmo.lab3.exceptions.ExceptionHandler;
import org.itmo.lab3.exceptions.NotEnoughtEnergy;
import org.itmo.lab3.inmaterials.Action;

import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.inmaterials.emotions.Emotion;
import org.itmo.lab3.places.Location;

public class Human extends Animal implements Inventory{

    protected String name;

    protected List<Thing> inventory;

    protected Smell smell;

    public Human(String name, Location loca) {
        this.name = name;
        this.location = loca;
        this.inventory = new ArrayList<Thing>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void doAction(Action action) {
        try {
            if (this.isAble(action)) {
                this.energyRate -= action.energyLoss;
                action.describe();
            }
        } catch (NotEnoughtEnergy e) {
            ExceptionHandler.handle(e);
        }
    }

    public boolean isAble(Action action) throws NotEnoughtEnergy {
        if (this.energyRate < action.energyLoss) {
            throw new NotEnoughtEnergy();
        } else {
            return true;
        }

    }

    @Override
    public void setEmotion(Emotion emotion) {
        this.emotionRate += emotion.getEmotionRateChange();
        emotion.describe();
    }

    @Override
    public EmotionStates getEmotionState() {
        if (emotionRate >= 100) {
            return EmotionStates.SUN;
        }
        else if (emotionRate >= 50) {
            return EmotionStates.CLOUDS;
        }
        return EmotionStates.RAIN;
    }

    @Override
    public void setLocation(Location loca) {
        this.location = loca;
        
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void showInventory() {
        for (Thing thing : this.inventory) {
            System.out.println(thing);
        }
    }

    @Override
    public void addToInventory(Thing thing) {
        inventory.add(thing);
    }

    @Override
    public void removeFromInventory(Thing thing) {
        inventory.remove(thing);
    }

    @Override
    public Smell getSmell() {
        return this.smell;
    }

    @Override
    public void setSmell(Smell smell) {
        this.smell = smell;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public boolean equals(Object thing) {
        if (thing instanceof Human) {
            return this.name == ((Human)thing).name;
        }
        return super.equals(thing);
    }
    
    @Override
    public int hashCode() {
        int result = this.getClass().hashCode();
        result = result + 31 * (this.name != null ? this.name.hashCode() : 0);
        return result;
    }

}
