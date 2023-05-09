package lab7.app.labwork.fields.author;

import lab7.app.labwork.Color;
import lab7.app.labwork.fields.Field;

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
        if (input == null) {
            return null;
        }
        return Color.valueOf(input);
    }

    public void putValue(Color value) {
        this.value = value;
    }


    public Color getValue() {
        return this.value;
    }

}
