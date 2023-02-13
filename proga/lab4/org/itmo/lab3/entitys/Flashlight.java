package org.itmo.lab3.entitys;

public class Flashlight extends Device{
    
    protected int lightLevel = 0;

    protected final int MAX_LIGHT_LEVEL = 10;

    public Flashlight(String description, Human owner) {
        super(description, owner);
    }
    
    public void upLightLevel(int val) {
        lightService lSrvs = new lightService() {
            public int change(int lvl, int ch) {
                return lvl + ch;
            }
        };
        int ch = lSrvs.change(this.lightLevel, val);
        this.lightLevel = ch < this.MAX_LIGHT_LEVEL ? ch : this.lightLevel;
    }

    public void downLightLevel(int val) {
        lightService lSrvs = new lightService() {
            public int change(int lvl, int ch) {
                return lvl - ch;
            }
        };
        int ch = lSrvs.change(this.lightLevel, val);
        this.lightLevel = ch >= 0 ? ch : this.lightLevel;
    }
}

@FunctionalInterface
interface lightService {
    public int change(int lvl, int ch);
}
