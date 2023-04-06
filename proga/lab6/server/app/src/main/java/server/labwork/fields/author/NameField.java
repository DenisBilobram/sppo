package server.labwork.fields.author;

import server.labwork.fields.Field;

public class NameField extends Field {

    private String value;
    
    @Override
    public boolean validate() {
        if (this.value == null || this.value.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public String toType(String input) {
        if (input == null) {
            return null;
        }
        return input;
    }

    public void putValue(String value) {
        this.value = value;
    }


    public String getValue() {
        return this.value;
    }
}
