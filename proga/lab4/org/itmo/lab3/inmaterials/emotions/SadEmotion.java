package org.itmo.lab3.inmaterials.emotions;

public class SadEmotion extends Emotion {
    public int emotionRateChange = -10;

    @Override
    public void describe() {
        System.out.println("So sad(\n");
    }
    
}
