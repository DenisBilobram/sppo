package org.itmo.lab3.inmaterials.emotions;

import org.itmo.lab3.inmaterials.Inmaterial;

public abstract class Emotion extends Inmaterial {
    protected int emotionRateChange;

    public int getEmotionRateChange() {
        return this.emotionRateChange;
    }

    public abstract void emotionEffect();
}