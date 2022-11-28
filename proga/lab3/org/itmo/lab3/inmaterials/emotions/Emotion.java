package org.itmo.lab3.inmaterials.emotions;

import org.itmo.lab3.inmaterials.Inmaterial;

public abstract class Emotion extends Inmaterial {
    public int emotionRateChange;
    public abstract void emotionEffect();
}