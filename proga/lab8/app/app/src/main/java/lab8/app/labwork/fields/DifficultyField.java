package lab8.app.labwork.fields;

import lab8.app.labwork.Difficulty;

public class DifficultyField extends Field {

    private Difficulty value;

    public boolean validate() {
        if (this.value == null) {
            return false;
        } else {
            return true;
        }
    }

    public Difficulty toType(String input) {
        if (input == null) {
            return null;
        }
        return Difficulty.valueOf(input);
    }

    public void putValue(Difficulty value) {
        this.value = value;
    }

    public Difficulty getValue() {
        return this.value;
    }
}
