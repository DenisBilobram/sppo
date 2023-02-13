package org.itmo.lab3.entitys;

import org.itmo.lab3.inmaterials.emotions.Emotion;

public interface Emotinal {
    public abstract void setEmotion(Emotion emotion);

    public abstract EmotionStates getEmotionState();
}
