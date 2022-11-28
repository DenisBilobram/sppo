package org.itmo.lab3.entitys;

import org.itmo.lab3.places.Smelling;

public abstract class Animal extends Entity implements Emotinal, Actionable, Smelling{
    public int emotionRate = 100;
}
