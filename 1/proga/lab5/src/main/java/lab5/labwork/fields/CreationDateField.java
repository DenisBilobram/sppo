package lab5.labwork.fields;

import java.util.Date;

public class CreationDateField extends Field {

    private Date value;

    @Override
    public boolean validate() {
        if (this.value == null) {
            return false;
        } else {
            return true;
        }
    }

    public Date toType(String input) {
        if (input == null) {
            return null;
        }
        throw new UnsupportedOperationException("Unimplemented method 'toType'");
    }

    public void putValue(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return this.value;
    }
    
}
