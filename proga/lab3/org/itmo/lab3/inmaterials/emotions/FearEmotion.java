package org.itmo.lab3.inmaterials.emotions;

public class FearEmotion extends Emotion{

    public int emotionRateChange = 10;

    @Override
    public void describe() {
        System.out.println("So scarry!\n");
    }
    
}
