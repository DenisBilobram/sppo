package org.itmo.lab3.entitys;

import java.util.ArrayList;
import java.util.List;

import org.itmo.lab3.inmaterials.Smell;
import org.itmo.lab3.inmaterials.emotions.Emotion;
import org.itmo.lab3.places.Location;
import org.itmo.lab3.places.Smelling;

public class Human extends Animal implements Inventory, Smelling{

    public List<Thing> inventory;

    public Smell smell;

    public Human(Location loca) {
        this.location = loca;
        this.inventory = new ArrayList<Thing>();
    }

    @Override
    public void setEmotion(Emotion emotion) {
        this.emotionRate += emotion.emotionRateChange;
        emotion.emotionEffect();
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
    
}
