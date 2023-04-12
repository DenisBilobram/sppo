package lab5.labwork.fields;

import lab5.labwork.Person;

public class AuthorField extends Field {

    private Person value;

    @Override
    public boolean validate() {
        if (this.value == null) {
            return false;
        } else {
            return true;
        }
    }

    public void putValue(Person value) {
        this.value = value;
    }
}
