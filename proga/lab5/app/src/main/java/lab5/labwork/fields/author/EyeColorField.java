package lab5.labwork.fields.author;

import lab5.labwork.Color;
import lab5.labwork.fields.Field;

public class EyeColorField extends Field {
    
    private Color value;

    @Override
    public boolean validate() {
        if (this.value == null) {
            return false;
        } else {
            return true;
        }
    }

    public Color toType(String input) {
        return Color.valueOf(input);
    }

    public void putValue(Color value) {
        this.value = value;
    }


    public Color getValue() {
        return this.value;
    }

}
