package org.itmo.lab3.entitys;

import org.itmo.lab3.inmaterials.Action;

public interface Actionable {
    public void doAction(Action action);

    public boolean isAble(Action action);
}

// 1) hashcode add and why 2) геттеры сеттеры на диаграмме 3) println отделить 