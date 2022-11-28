package org.itmo.lab3.entitys;

import org.itmo.lab3.inmaterials.Action;

public interface Actionable {
    default void doAction(Action action) {
        System.out.println(action.description);
    }
}
