package lab7.app.labwork.fields.user;

import lab7.app.labwork.fields.Field;

public class PasswordField extends Field {

    String value;

    @Override
    public boolean validate() {
        if (value == null) return false;
        if (value.length() < 5) {
            System.out.println("Слишком короткий пароль.");
            return false;
        } else if (value.length() > 40) {
            System.out.println("Слишком длинный парль.");
            return false;
        }
        return true;
    }

    public String getValue() {
        return value;
    }

    public void putValue(String value) {
        this.value = value;
    }

    public String toType(String input) {
        return input;
    }
    
}
