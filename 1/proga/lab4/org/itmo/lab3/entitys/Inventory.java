package org.itmo.lab3.entitys;

public interface Inventory {
    public void showInventory();

    public void addToInventory(Thing thing);

    public void removeFromInventory(Thing thing);
}
