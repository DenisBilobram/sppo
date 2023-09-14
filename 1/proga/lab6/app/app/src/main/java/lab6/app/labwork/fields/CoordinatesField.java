package lab6.app.labwork.fields;

import lab6.app.labwork.Coordinates;

public class CoordinatesField extends Field {

    private Coordinates value;

    @Override
    public boolean validate() {
        if (this.value == null || this.value.getX() > 689 || this.value.getY() > 475) {
            return false;
        } else {
            return true;
        }
    }

    public Coordinates toType(String input) {
        if (input == null) {
            return null;
        }
        String[] xy = input.split(" ");
        return new Coordinates(Long.parseLong(xy[0]), Long.parseLong(xy[1]));

    }

    public void putValue(Coordinates value) {
        this.value = value;
    }

    public Coordinates getValue() {
        return this.value;
    }
    
}
