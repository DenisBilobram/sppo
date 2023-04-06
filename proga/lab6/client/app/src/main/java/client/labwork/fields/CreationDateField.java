package client.labwork.fields;

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
        throw new UnsupportedOperationException("Unimplemented method 'toType'");
    }

    public void putValue(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return this.value;
    }
    
}
