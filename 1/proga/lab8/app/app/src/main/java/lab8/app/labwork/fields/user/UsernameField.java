package lab8.app.labwork.fields.user;

import lab8.app.labwork.fields.Field;

public class UsernameField extends Field {

    String value;

    @Override
    public boolean validate() {
        if (value == null) return false;
        if (value.length() < 5) {
            System.out.println("Слишком короткое имя пользователя.");
            return false;
        } else if (value.length() > 30) {
            System.out.println("Слишком длинное имя пользователя.");
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
