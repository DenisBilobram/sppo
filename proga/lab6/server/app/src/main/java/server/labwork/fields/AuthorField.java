package server.labwork.fields;

import server.labwork.Person;

public class AuthorField extends Field {

    private Person value;

    @Override
    public boolean validate() {
        if (this.value == null || this.value.getName() == null || this.value.getName().equals("") || this.value.getHeigth() == null || this.value.getHeigth() <= 0 || this.value.getEyeColor() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void putValue(Person value) {
        this.value = value;
    }
}
