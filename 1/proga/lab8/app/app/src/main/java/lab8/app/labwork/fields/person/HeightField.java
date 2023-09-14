package lab8.app.labwork.fields.person;

import lab8.app.labwork.fields.Field;

public class HeightField extends Field {

    private Long value;
    
    @Override
    public boolean validate() {
        if (this.value == null || this.value <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public Long toType(String input) {
        if (input == null) {
            return null;
        }

        return Long.parseLong(input);
    }

    public void putValue(Long value) {
        this.value = value;
    }


    public Long getValue() {
        return this.value;
    }
}
